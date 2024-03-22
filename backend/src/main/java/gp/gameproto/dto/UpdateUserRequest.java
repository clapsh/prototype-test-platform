package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRequest {
    private String name;
    private Gender gender;
    private Integer age;
    private String nation;
    private String bio;
    private Category favCategory1;
    private Category favCategory2;
    private Category favCategory3;
}
