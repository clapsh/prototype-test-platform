package gp.gameproto.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gp.gameproto.dto.UpdateReviewRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근제어자가 protected인 기본생성자 생성
@Transactional(readOnly = true)//transaction 단위에 따라 1차캐시영역에 있는 객체들이 db에 flush되어 영속화되기 때문이다.
@Table(name = "Review")
public class Review {
    @Id
    @Column(name = "review_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    private Character deleted;

    @Column(nullable = false)
    private String status;

    // 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 연관관계 연결을 위한 column
    private User user; // 연관관계 주인


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    // 객체 생성자
    @Builder
    public Review(String text, LocalDateTime createdAt, LocalDateTime modifiedAt, Character deleted,String status ){
        this.text = text;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.status = status;
    }

    // User와 연관관계 매핑
    public void mappingUser(User user){
        this.user = user;
    }

    // Test와 연관관계 매핑
    public void mappingTest(Test test){
        this.test = test;
    }

    // 리뷰 수정
    public Review update (UpdateReviewRequest request){
        this.text = request.getText();
        this.modifiedAt = LocalDateTime.now();

        return this;
    }

    // 리뷰 삭제
    public void delete(){
        this.deleted = 'Y';
    }

    // 리뷰 반영 상태 변경
    public Review updateStatus(String status){
        this.status = status;
        return this;
    }

}
