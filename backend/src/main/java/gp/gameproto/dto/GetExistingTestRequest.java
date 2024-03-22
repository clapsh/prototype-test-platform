package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetExistingTestRequest {
    private Integer round;
    private String gameName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime reviewDate;
    private Integer recruitedTotal;
    private String imgPath;
    private Category category;

    public GetExistingTestRequest(Test test){
        this.round = test.getRound();
        this.gameName = test.getGame().getName();
        this.description = test.getDescription();
        this.startDate = test.getStartDate();
        this.endDate = test.getEndDate();
        this.reviewDate = test.getReviewDate();
        this.recruitedTotal = test.getRecruitedTotal();
        this.imgPath = test.getImgPath();
        this.category = test.getGame().getCategory();
    }
}
