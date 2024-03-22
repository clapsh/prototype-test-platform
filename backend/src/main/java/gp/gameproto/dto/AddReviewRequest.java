package gp.gameproto.dto;

import gp.gameproto.db.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReviewRequest {
    private String text;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
    private Character deleted = 'N'; // default value
    private String status = "N"; // default value

    public Review toEntity() {
        return Review.builder() // dto -> db에 저장할 엔티티로 사용
                .text(text)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deleted(deleted)
                .status(status)
                .build();
    }

}
