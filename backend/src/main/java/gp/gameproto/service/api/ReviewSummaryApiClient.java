package gp.gameproto.service.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import gp.gameproto.db.entity.ReviewSummary;
import gp.gameproto.dto.*;
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
    private final String CLIENT_ID = "";
    private final String CLIENT_SECRET = "";

    private final String ClovaSummaryUrl = "https://naveropenapi.apigw.ntruss.com";

    // 리뷰 데이터 합지는 로직 필요
    
    // Create request body
    Document document = new Document("'하루 2000억' 판 커지는 간편송금 시장","\"한국은행이 17일 발표한 '2019년 상반기중 전자지급서비스 이용 현황'에 따르면 올해 상반기 간편송금서비스 이용금액(일평균)은 지난해 하반기 대비 60.7% 증가한 2005억원으로 집계됐다. 같은 기간 이용건수(일평균)는 34.8% 늘어난 218만건이었다. 간편 송금 시장에는 선불전자지급서비스를 제공하는 전자금융업자와 금융기관 등이 참여하고 있다. 이용금액은 전자금융업자가 하루평균 1879억원, 금융기관이 126억원이었다. 한은은 카카오페이, 토스 등 간편송금 서비스를 제공하는 업체 간 경쟁이 심화되면서 이용규모가 크게 확대됐다고 분석했다. 국회 정무위원회 소속 바른미래당 유의동 의원에 따르면 카카오페이, 토스 등 선불전자지급서비스 제공업체는 지난해 마케팅 비용으로 1000억원 이상을 지출했다. 마케팅 비용 지출규모는 카카오페이가 491억원, 비바리퍼블리카(토스)가 134억원 등 순으로 많았다.");
    Option option = new Option("ko", "general", 2, 3);
    Body body = new Body(document, option);

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
        try {
           bodyJson  = mapper.writeValueAsString(body);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // System.out.println(bodyJson);
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
