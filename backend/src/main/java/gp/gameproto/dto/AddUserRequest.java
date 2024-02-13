package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Gender;
import gp.gameproto.db.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddUserRequest {
    private String email;
    private String pw;
    private String name;
    private String bio;
    private Gender gender;
    private String nation;
    private Integer age;
    private String imgPath;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
    private Category favCategory1;
    private Category favCategory2;
    private Category favCategory3;


}
