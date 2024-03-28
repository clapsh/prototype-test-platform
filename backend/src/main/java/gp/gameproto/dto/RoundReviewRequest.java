package gp.gameproto.dto;

import lombok.Getter;

@Getter
public class RoundReviewRequest {
    private Long gameId;
    private Integer round;
    private boolean all;
}
