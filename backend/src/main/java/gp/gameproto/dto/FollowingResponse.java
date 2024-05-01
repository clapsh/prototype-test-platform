package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowingResponse {
    private String imgPath;
    private String userName;
    private String userEmail;
    private Category category1;
    private Category category2;
    private Category category3;

    public FollowingResponse(User following){
        this.imgPath = following.getImgPath();
        this.userName = following.getName();
        this.userEmail = following.getEmail();
        this.category1 = following.getFavCategory1();
        this.category2 = following.getFavCategory2();
        this.category3 = following.getFavCategory3();
    }

}
