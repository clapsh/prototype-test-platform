package gp.gameproto.dto;

import lombok.Getter;

@Getter
public class UpdateReviewStatusRequest {
    private String testUserEmail;
    private String reviewReflected;
}
