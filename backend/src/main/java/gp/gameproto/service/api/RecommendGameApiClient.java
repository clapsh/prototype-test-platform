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
import gp.gameproto.dto.AiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommendGameApiClient {
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    private final String recommendUrl = "https://f67c-34-170-50-131.ngrok-free.app";
    public List<Test> findAIRecommendTests(String email){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        // 사용자가 참여한 최근 게임
        int lastIdx = user.getPlayedTestList().size() -1;
        Test test;
        Long playedTestId = -1L;
        if (lastIdx > -1){// 참여한 게임이 있을 경우
            Long testId = user.getPlayedTestList().get(lastIdx);
            playedTestId = testId;
            // 테스트 찾기
            test = testRepository.findById(testId)
                    .orElseThrow(()-> new IllegalArgumentException("not found: "+ testId));
        }else{//참여한 게임이 없는 경우(카테고리 기반 추천)
            Category userFavCat = user.getFavCategory1();
            List<Test> categorizedGameList = testRepository.findCategorizedGameList(userFavCat).orElseThrow(()->new IllegalArgumentException("선호하는 카테고리의 게임이 없습니다."));
            test = categorizedGameList.get(categorizedGameList.size()-1);
        }

        AiRecommendBody recommendBody = new AiRecommendBody(test.getDescription());
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
        AiResponseBody resp = webClient
                .post()
                .uri("/predict")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<AiResponseBody/*List<Test>*/>() {})//Map.class) //  bodyToMono : response body 데이터를 얻기 위해 사용
                .block();


        //944\n1633\n1554\n1817\n1153\n945\n1616\n1508\n1547\n1061\n
        List<Test> response = new ArrayList<>();

        String[] testIds = resp.getPrediction().split(" ");

        for(String id: testIds){
            if (id == " "){
                continue;
            }
            Long testId = Long.parseLong(id);
            System.out.println(testId+"s");
            if (testId != playedTestId){
                continue;
            }
            Test found_test = testRepository.findById(testId)
                    .orElseThrow(()->new IllegalArgumentException("not found: "+ testId));
            response.add(found_test);
        }
        return response;
    }

}
