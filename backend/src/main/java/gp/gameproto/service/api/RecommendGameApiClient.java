package gp.gameproto.service.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.TestRepository;
import gp.gameproto.db.repository.UserRepository;
import gp.gameproto.dto.AiRecommendBody;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommendGameApiClient {
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    private final String recommendUrl = "http://127.0.0.1:8888";
    public List<Test> findAIRecommendTests(String email){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        // 사용자가 참여한 최근 게임
        int lastIdx = user.getPlayedTestList().size() -1;
        Test test;
        if (lastIdx > -1){// 참여한 게임이 있을 경우
            Long testId = user.getPlayedTestList().get(lastIdx);
            // 테스트 찾기
            test = testRepository.findById(testId)
                    .orElseThrow(()-> new IllegalArgumentException("not found: "+ testId));
        }else{//참여한 게임이 없는 경우(카테고리 기반 추천)
            Category userFavCat = user.getFavCategory1();
            List<Test> categorizedGameList = testRepository.findCategorizedGameList(userFavCat).orElseThrow(()->new IllegalArgumentException("선호하는 카테고리의 게임이 없습니다."));
            test = categorizedGameList.get(categorizedGameList.size()-1);
        }

        AiRecommendBody recommendBody = new AiRecommendBody(test.getId(), test.getDescription());
        //api 연결
        // webClient 기본 설정
        WebClient webClient = WebClient
                .builder()
                .baseUrl(recommendUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = "";
        try {
            bodyJson  = mapper.writeValueAsString(recommendBody);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         System.out.println(bodyJson);
        // api 요청
        List<Test> response = webClient
                .post()
                .uri("/recommend")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Test>>() {})//Map.class) //  bodyToMono : response body 데이터를 얻기 위해 사용
                .block();


        return response;
    }

}
