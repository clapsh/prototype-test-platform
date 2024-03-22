package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.Getter;

@Getter
public class RecentTestsResponse {
    private Long gameId;
    private String gameName;
    private String imgPath;
    private Long testId;
    private Category category;

    public RecentTestsResponse(Test test){
        this.gameId = test.getGame().getId();
        this.gameName = test.getGame().getName();
        this.imgPath = test.getImgPath();
        this.testId = test.getId();
        this.category = test.getGame().getCategory();

    }
}
