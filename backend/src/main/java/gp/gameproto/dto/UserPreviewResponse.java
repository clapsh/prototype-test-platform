package gp.gameproto.dto;

import gp.gameproto.db.entity.User;
import lombok.Getter;

@Getter
public class UserPreviewResponse {
    private final String name;
    private final String bio;
    private final String imgPath;

    public UserPreviewResponse(User user) {
        this.name = user.getName();
        this.bio = user.getBio();
        this.imgPath = user.getImgPath();
    }
}
