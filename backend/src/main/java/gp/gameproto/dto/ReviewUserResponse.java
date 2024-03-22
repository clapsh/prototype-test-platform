package gp.gameproto.dto;

import gp.gameproto.db.entity.Gender;
import gp.gameproto.db.entity.Review;
import lombok.Getter;

@Getter
public class ReviewUserResponse {
    private Long reviewId;
    private String reviewText;
    private String gameName;
    private String imgPath;
    private Long testId;
    private Integer testRound;

    public ReviewUserResponse(Review review){
        this.reviewId = review.getId();
        this.reviewText = review.getText();
        this.gameName = review.getTest().getGame().getName();
        this.imgPath = review.getTest().getImgPath();
        this.testId = review.getTest().getId();
        this.testRound = review.getTest().getRound();
    }
}
