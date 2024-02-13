package gp.gameproto.controller;

import gp.gameproto.dto.AddReviewRequest;
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

    @PostMapping
    public ResponseEntity<Long> addReview(@RequestParam(value = "test_id") Long test_id, @RequestBody AddReviewRequest request){
         Long savedReviewId = reviewService.save(request);
         return ResponseEntity.status(HttpStatus.CREATED) // 201
                 .body(savedReviewId); // 저장된 리뷰 아이디를 응답 body에 반환
    }

    @GetMapping
    /*public ResponseEntity<List<Review>> findAllReviews(){
        List<Review> reviews =
    }*/
    public String find(@RequestParam(value = "test_id") Long testId){
        return ("hi"+testId);
    }

    /*@GetMapping
    public ResponseEntity<List<Review>> findAllReviews(@RequestParam(value = "user_id") Long userId){
        List<Review> reviews =
    }*/


}
