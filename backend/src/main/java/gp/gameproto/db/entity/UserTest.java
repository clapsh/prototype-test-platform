package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;
////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DELETE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "UserTest")
public class UserTest {
    @Id
    @Column(name = "user_test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

}
