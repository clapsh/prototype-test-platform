package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import gp.gameproto.db.entity.User;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Follow")
public class Follow {
    @Id
    @Column(name = "follow_id", updatable = false)
    private Long id; // follower(fromUser)+followee(toUSer) 로 관리

    @Column(nullable = false)
    private Long fromUserId;

    @Column(nullable = false)
    private Long toUserId;

    // 생성자
    public Follow(Long fromUserId, Long toUserId){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "user_id") // 연관관계 연결을 위한 column
    private User user; // 연관관계 주인

}
