package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    /*@Column(nullable = false)
    private String status;*/

    // 연관관계 매핑
    @ManyToOne//(fetch = FetchType.LAZY) // 성능 최적화를 위함.
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    // 생성자
    @Builder
    public Dibs(User user, Test test,LocalDateTime createdAt){
        this.user = user;
        this.test = test;
        this.createdAt = createdAt;
    }
/*
    // user 객체 생성 (연관관계 매핑)
    public void mappingUser (User user){
        this.user = user;
    }

    // test 객체 생성 (연관관계 매핑)
    public void mappingTest (Test test){
        this.test = test;
        test.addDibs(this);
    }
*/
}
