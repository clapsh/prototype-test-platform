package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Gender;
import gp.gameproto.db.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private final String email;
   // private final String pw;
    private final String name;
    private final String bio;
    private final Gender gender;
    private final String nation;
    private final Integer age;
    private final String imgPath;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Category favCategory1;
    private final Category favCategory2;
    private final Category favCategory3;


    public UserResponse(User user) {
        this.email = user.getEmail();
     //   this.pw = user.getPw();
        this.name = user.getName();
        this.bio = user.getBio();
        this.gender = user.getGender();
        this.nation = user.getNation();
        this.age = user.getAge();
        this.imgPath = user.getImgPath();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.favCategory1 = user.getFavCategory1();
        this.favCategory2 = user.getFavCategory2();
        this.favCategory3 = user.getFavCategory3();
    }
}
