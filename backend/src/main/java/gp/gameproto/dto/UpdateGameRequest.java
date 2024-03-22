package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateGameRequest {
    private String name;
    private Category category;
}
