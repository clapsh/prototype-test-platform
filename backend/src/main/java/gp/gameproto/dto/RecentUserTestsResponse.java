package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.Getter;

@Getter
public class RecentUserTestsResponse {
    private Long testId;
    private String imgPath;
    private String gameName;
    private int round;
    private Category category;
    private Long gameId;

    public RecentUserTestsResponse(Test test){
        this.testId = test.getId();
        this.imgPath = test.getImgPath();
        this.gameName = test.getGame().getName();
        this.round = test.getRound();
        this.category = test.getGame().getCategory();
        this.gameId = test.getGame().getId();
    }
}
