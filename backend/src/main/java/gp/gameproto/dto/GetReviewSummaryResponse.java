package gp.gameproto.dto;

import gp.gameproto.db.entity.ReviewSummary;
import lombok.Getter;

@Getter
public class GetReviewSummaryResponse {
    private Long testId;
    private Integer testRound;
    private String reviewSummaryText;

    public GetReviewSummaryResponse(ReviewSummary summary){
        this.testId = summary.getTest().getId();
        this.testRound = summary.getTest().getRound();
        this.reviewSummaryText = summary.getSummary();
    }
}
