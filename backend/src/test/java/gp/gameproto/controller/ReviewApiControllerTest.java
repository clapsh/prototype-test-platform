package gp.gameproto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gp.gameproto.db.entity.Review;
import gp.gameproto.db.repository.ReviewRepository;
import gp.gameproto.dto.AddReviewRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        //reviewRepository.deleteAll();

    }

    @DisplayName("리뷰 등록 성공")
    @Test
    public void addReview() throws Exception{
        //given
        final String url = "/review";
        final String test_id = "1";
        final String text = "첫 리뷰 개시";
        final AddReviewRequest userRequest = new AddReviewRequest(text, LocalDateTime.now(), LocalDateTime.now(),'N',"N");

        // JSON 객체로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .param("test_id",test_id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews.size()).isEqualTo(1);
        assertThat(reviews.get(0).getText()).isEqualTo(text);
    }
}