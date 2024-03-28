package gp.gameproto.dto;

import gp.gameproto.db.entity.Gender;
import gp.gameproto.db.entity.Review;
import lombok.Getter;

@Getter
public class ReviewSearchResponse {
    private Long reviewId;
    private String reviewText;
    private String reviewReflected;
    private String userName;
    private Gender userGender;
    private Long testId;
    private Integer testRound;

    public ReviewSearchResponse(Review review){
        this.reviewId = review.getId();
        this.reviewText = review.getText();
        this.reviewReflected = review.getStatus();
        this.userName = review.getUser().getName();
        this.userGender = review.getUser().getGender();
        this.testId = review.getTest().getId();
        this.testRound = review.getTest().getRound();
    }
}
