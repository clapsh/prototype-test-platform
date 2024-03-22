package gp.gameproto.controller;

import gp.gameproto.dto.AddReviewRequest;
import gp.gameproto.dto.GetReviewResponse;
import gp.gameproto.dto.GetReviewUserResponse;
import gp.gameproto.dto.UpdateReviewRequest;
import gp.gameproto.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gp.gameproto.db.entity.Review;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewApiController {
    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/{testId}")
    public ResponseEntity<Long> addReview(@PathVariable("testId") Long test_id,@RequestParam(value = "email") String email
            , @RequestBody AddReviewRequest request){
         Long savedReviewId = reviewService.save(request, test_id, email);
         return ResponseEntity.status(HttpStatus.CREATED) // 201
                 .body(savedReviewId); // 저장된 리뷰 아이디를 응답 body에 반환
    }

    // 프로젝트별 리뷰 불러오기
    @GetMapping("/{testId}")
    public ResponseEntity<GetReviewResponse> findAllReviews(@PathVariable("testId") Long testId){
        List<Review> reviews = reviewService.findReviewsOfTest(testId);
        // reviews가 empty일 경우 예외 처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetReviewResponse(reviews));
    }


    // 유저별 리뷰 불러오기
    @GetMapping()
    public ResponseEntity<GetReviewUserResponse> findAllUserReviews(@RequestParam(value = "email") String email){
        List<Review> reviews = reviewService.findReviewsOfUser(email);
        // reviews가 empty일 경우 예외 처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetReviewUserResponse(reviews));
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable("reviewId") Long reviewId, @RequestParam(value = "email") String email
            , @RequestBody UpdateReviewRequest request){
        Review updatedReview = reviewService.update(request, email, reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedReview);
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId")Long id, @RequestParam(value="email") String email){
        String message = reviewService.delete(email, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }


}
