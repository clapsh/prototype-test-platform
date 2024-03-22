package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

@Getter
public class CategorizedTestsResponse {
    private Long gameId;
    private String gameName;
    private String imgPath;
    private Long testId;

    public CategorizedTestsResponse(Test test){
        this.gameId = test.getGame().getId();
        this.gameName = test.getGame().getName();
        this.imgPath = test.getImgPath();
        this.testId = test.getId();
    }
}
