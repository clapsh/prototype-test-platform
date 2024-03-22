package gp.gameproto.dto;

import gp.gameproto.db.entity.Review;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetReviewUserResponse {
    private Integer reviewsLen;
    private List<ReviewUserResponse> reviewList;

    public GetReviewUserResponse(List<Review> reviews){
        // ReviewResponse list 로 변환
        List<ReviewUserResponse> recentReviews = new ArrayList<>();
        for(Review review:reviews){
            ReviewUserResponse dto = new ReviewUserResponse(review);
            recentReviews.add(dto);
        }
        this.reviewList = recentReviews;
        this.reviewsLen = recentReviews.size();
    }
}
