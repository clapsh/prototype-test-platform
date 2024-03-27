package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.Getter;

@Getter
public class Top10Response {
    private Long gameId;
    private String gameName;
    private String imgPath;
    private Long testId;
    private Category category;

    public Top10Response(Test test){
        this.gameId = test.getGame().getId();
        this.gameName = test.getGame().getName();
        this.category = test.getGame().getCategory();
        this.imgPath = test.getImgPath();
        this.testId = test.getId();
    }
}
