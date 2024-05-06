package gp.gameproto.dto;

import gp.gameproto.db.entity.Gender;
import gp.gameproto.db.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {
    private Long reviewId;
    private String reviewText;
    private String reviewReflected;
    private String userName;
    private Gender userGender;
    private String userEmail;

    public ReviewResponse(Review review){
        this.reviewId = review.getId();
        this.reviewText = review.getText();
        this.reviewReflected = review.getStatus();
        this.userName = review.getUser().getName();
        this.userGender = review.getUser().getGender();
        this.userEmail = review.getUser().getEmail();
    }
}
