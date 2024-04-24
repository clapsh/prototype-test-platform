package gp.gameproto.controller;

import gp.gameproto.db.entity.ReviewSummary;
import gp.gameproto.dto.GetReviewSummaryResponse;
import gp.gameproto.service.ReviewSummaryService;
import gp.gameproto.service.api.ReviewSummaryApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewSummaryApiController {
    private final ReviewSummaryService reviewSummaryService;
    private final ReviewSummaryApiClient reviewSummaryApiClient;
    // (백엔드)리뷰 요약 요청
    //@PostMapping("/summary")
    //public
    // (프론트)리뷰 요약 응답 -> 백엔드에서 요청 후 응답
    @GetMapping("/summary/{testId}") // 저장하는 방식 말고 매번 요청 ???
    public ResponseEntity<GetReviewSummaryResponse> getReviewSummary(@PathVariable("testId")Long testId){
        Optional<ReviewSummary> summary = reviewSummaryService.findByTestId(testId);

        ReviewSummary reviewSummary;
        // 요약 요청
        if(summary.isEmpty()){
            reviewSummary = reviewSummaryApiClient.requestSummary(testId);
            // 저장
            reviewSummaryService.save(reviewSummary,testId);
        }else{
            reviewSummary = summary.orElseThrow(()->new IllegalArgumentException("리뷰 요약 오류 발생"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetReviewSummaryResponse(reviewSummary));
    }

}
