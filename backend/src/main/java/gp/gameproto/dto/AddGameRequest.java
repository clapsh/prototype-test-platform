package gp.gameproto.dto;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddGameRequest {
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Category category;

    public Game toEntity(){
        return Game.builder()
                .name(name)
                .createdAt(createdAt)
                .category(category)
                .build();
    }

}
