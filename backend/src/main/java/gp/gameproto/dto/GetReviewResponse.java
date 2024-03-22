package gp.gameproto.dto;

import gp.gameproto.db.entity.Review;
import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetReviewResponse {
    private Long testId;
    private Integer testRound;
    private Integer reviewsLen;
    private List<ReviewResponse> reviewList;
    
    public GetReviewResponse(List<Review> reviews){
        // test id, test round 설정
        if (reviews.get(0) != null){
            Review review = reviews.get(0);
            this.testId = review.getTest().getId();
            this.testRound = review.getTest().getRound();
        }
        // ReviewResponse list 로 변환
        List<ReviewResponse> recentReviews = new ArrayList<>();
        for(Review review:reviews){
            ReviewResponse dto = new ReviewResponse(review);
            recentReviews.add(dto);
        }
        this.reviewList = recentReviews;
        this.reviewsLen = recentReviews.size();
    }
}
