package gp.gameproto.dto;

import gp.gameproto.db.entity.Review;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRoundReviewResponse {
    private Integer reviewsCnt;
    private List<RoundReviewResponse> reviewList;

    public GetRoundReviewResponse(List<Review> reviews){
        List<RoundReviewResponse> roundReviews = new ArrayList<>();
        for(Review review: reviews){
            RoundReviewResponse dto = new RoundReviewResponse(review);
            roundReviews.add(dto);
        }
        this.reviewList = roundReviews;
        this.reviewsCnt = roundReviews.size();
    }
}
