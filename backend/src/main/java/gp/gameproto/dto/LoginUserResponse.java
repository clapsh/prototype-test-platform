package gp.gameproto.dto;

import gp.gameproto.db.entity.User;
import lombok.Getter;

@Getter
public class LoginUserResponse {
    private Long userId;
    private String email;
    private String imgPath;

    public LoginUserResponse(User user){
        this.userId = user.getId();
        this.email = user.getEmail();
        this.imgPath = user.getImgPath();
    }
}
