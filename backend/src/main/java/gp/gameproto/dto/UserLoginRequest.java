package gp.gameproto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserLoginRequest {
    private String email;
    private String pw;
}
