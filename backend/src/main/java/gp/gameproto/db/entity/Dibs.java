package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Dibs")
public class Dibs {
    @Id
    @Column(name = "dibs_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    private String status;

    // 생성자
    public Dibs(LocalDateTime createdAt, String status){
        this.createdAt = createdAt;
        this.status = status;
    }

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

}
