package gp.gameproto.db.entity;

import gp.gameproto.dto.UpdateGameRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Game")
public class Game {
    @Id
    @Column(name = "game_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Category category;

    // 생성자
    @Builder
    public Game(String name, LocalDateTime createdAt, Category category){
        this.name = name;
        this.createdAt = createdAt;
        this.category = category;
    }

    // 연관관계 매핑 (양방향)?
    /*@OneToMany(mappedBy = "game")
    private List<Test> testList;*/


    // 게임 정보 수정
    public Game update(UpdateGameRequest request){
        this.name = request.getName();
        this.category = request.getCategory();
        return this;
    }



}
