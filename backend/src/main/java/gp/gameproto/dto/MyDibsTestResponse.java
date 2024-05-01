package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.Getter;
///게임 이름, 회차, 카테고리 , 리뷰수, 이미지
@Getter
public class MyDibsTestResponse {
    private String gameName;
    private Integer round;
    private Category category;
    private Integer reviewCnt;
    private String imgPath;
    private Long testId;
    private Long gameId;

    public MyDibsTestResponse(Test test){
        this.gameName = test.getGame().getName();
        this.round = test.getRound();
        this.category = test.getGame().getCategory();
        this.reviewCnt = test.getReviewList().size();
        this.imgPath = test.getImgPath();
        this.testId = test.getId();
        this.gameId = test.getGame().getId();
    }
}
