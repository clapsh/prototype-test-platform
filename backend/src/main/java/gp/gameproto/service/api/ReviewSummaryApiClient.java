package gp.gameproto.service.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import gp.gameproto.db.entity.ReviewSummary;
import gp.gameproto.dto.*;
import gp.gameproto.service.ReviewService;
import gp.gameproto.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ReviewSummaryApiClient {
    /*GITHUB에 올릴 때 시크릿변수에 저장!!!!*/
    private final String CLIENT_ID = "oj91dxvnxp";
    private final String CLIENT_SECRET = "HC34KIztPISzzeVlGbUCgI3GpfuqEuyrT6SZMpXt";

    private final String ClovaSummaryUrl = "https://naveropenapi.apigw.ntruss.com";
    private final ReviewService reviewService;
    public ReviewSummary requestSummary(Long testId){
        //헤더 설정
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", CLIENT_SECRET);
        headers.set("Content-Type", "application/json");

        // webClient 기본 설정
        WebClient webClient = WebClient
                                    .builder()
                                    .baseUrl(ClovaSummaryUrl)
                                    .build();

        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = "";
        // 리뷰 데이터 합지는 로직
        String content = reviewService.getAllTestReviews(testId);

        // Create request body
        Document document = new Document("게임 리뷰 요약",content);
        Option option = new Option("ko", "general", 2, 3);
        Body body = new Body(document, option);

        try {
           bodyJson  = mapper.writeValueAsString(body);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(bodyJson);
        // api 요청
        SummaryResponseDto response = webClient
                        .post()
                        .uri("/text-summary/v1/summarize")
                        .headers(h -> h.addAll(headers))
                        .bodyValue(bodyJson)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<SummaryResponseDto>() {})//Map.class) //  bodyToMono : response body 데이터를 얻기 위해 사용
                        .block();

        // 결과
        ReviewSummary reviewSummary = new AddReviewSummaryRequest().toEntity(response.getSummary());


        return reviewSummary;
    }

}
