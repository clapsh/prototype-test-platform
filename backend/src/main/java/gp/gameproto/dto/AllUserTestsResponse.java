package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

@Getter
public class AllUserTestsResponse {
    private Long gameId;
    private String gameName;
    private String imgPath;
    private Long testId;
    private Integer testRound;
    private Integer reviewCount;

    public AllUserTestsResponse(Test test){
        this.gameId = test.getGame().getId();
        this.gameName = test.getGame().getName();
        this.imgPath = test.getImgPath();
        this.testId = test.getId();
        this.testRound = test.getRound();
        this.reviewCount = test.getReviewList().size();
    }
}
