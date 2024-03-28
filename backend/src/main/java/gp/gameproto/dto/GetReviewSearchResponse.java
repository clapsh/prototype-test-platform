package gp.gameproto.dto;

import gp.gameproto.db.entity.Review;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetReviewSearchResponse {
    private Integer reviewsLen;
    private List<ReviewSearchResponse> reviewList;

    public GetReviewSearchResponse(List<Review> reviews){
        // ReviewSearchResponse list 로 변환
        List<ReviewSearchResponse> searchedReviews = new ArrayList<>();
        for(Review review:reviews){
            ReviewSearchResponse dto = new ReviewSearchResponse(review);
            searchedReviews.add(dto);
        }
        this.reviewList = searchedReviews;
        this.reviewsLen = searchedReviews.size();
    }
}
