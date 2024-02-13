package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;

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

    // 생성자
    public Game(String name){
        this.name = name;
    }

    // 연관관계 매핑
    @OneToMany(mappedBy = "game")
    private List<Test> testList;



}
