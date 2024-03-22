package gp.gameproto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTestRequest {
    private Integer round;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime reviewDate;
    private LocalDateTime modifiedAt;
    private Integer recruitedTotal;
    private String downloadLink;
    private String imgPath;
}
