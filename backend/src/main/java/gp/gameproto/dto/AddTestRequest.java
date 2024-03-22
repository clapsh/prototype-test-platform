package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddTestRequest {
    private Integer round;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime reviewDate;//테스트리뷰 종료 일자
    private LocalDateTime createdAt = LocalDateTime.now();
    private Integer recruitedTotal;
    private String downloadLink;
    private Character deleted = 'N';
    private String imgPath;
    private String status;
    public Test toEntity(){
        return Test.builder()
                .round(round)
                .createdAt(createdAt)
                .deleted(deleted)
                .description(description)
                .downloadLink(downloadLink)
                .endDate(endDate)
                .imgPath(imgPath)
                .recruitedTotal(recruitedTotal)
                .status(status)
                .reviewDate(reviewDate)
                .startDate(startDate)
                .build();
    }


}
