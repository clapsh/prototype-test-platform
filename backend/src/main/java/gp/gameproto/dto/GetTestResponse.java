package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetTestResponse {
    private Integer round;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime reviewDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer recruitedTotal;
    private String downloadLink;
    private String imgPath;
    private String gameName;
    private Category category;
    public GetTestResponse(Test test){
        this.round = test.getRound();
        this.description = test.getDescription();
        this.startDate = test.getStartDate();
        this.endDate = test.getEndDate();
        this.reviewDate = test.getReviewDate();
        this.createdAt = test.getCreatedAt();
        this.modifiedAt = test.getModifiedAt();
        this.recruitedTotal = test.getRecruitedTotal();
        this.downloadLink = test.getDownloadLink();
        this.imgPath = test.getImgPath();
        this.gameName = test.getGame().getName();
        this.category = test.getGame().getCategory();
    }
}
