package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GameCategory")
public class GameCategory {
    @Id
    @Column(name = "game_category_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Category category;

    // 생성자
    @Builder
    public GameCategory(Category category){
        this.category = category;
    }

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
