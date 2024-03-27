package gp.gameproto.dto;

import lombok.Getter;

@Getter
public class GetRoundReviewRequest {
    private Long gameId;
    private Integer round;
    private boolean all;
}
