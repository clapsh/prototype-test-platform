package gp.gameproto.dto;

import gp.gameproto.db.entity.ReviewSummary;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddReviewSummaryRequest {
    private String summary;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status = "N";

    public ReviewSummary toEntity (String summary){
        return ReviewSummary.builder()
                .summary(summary)
                .createdAt(createdAt)
                .status(status)
                .build();
    }
}
