package gp.gameproto.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DibsRequest {
    private Long testId;
    private String email;

    public DibsRequest(Long testId, String email){
        this.testId = testId;
        this.email = email;
    }
}
