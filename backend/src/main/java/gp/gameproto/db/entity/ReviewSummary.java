package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import gp.gameproto.db.entity.Test;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ReviewSummary")
public class ReviewSummary {
    @Id
    @Column(name = "review_summary_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String summary;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String status;

    // 생성자
    @Builder
    public ReviewSummary(String summary, LocalDateTime createdAt, String status){
        this.summary = summary;
        this.createdAt = createdAt;
        this.status = status;
    }

    // 연관관계 매핑
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

}
