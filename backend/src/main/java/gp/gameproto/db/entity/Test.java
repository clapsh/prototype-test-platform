package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import gp.gameproto.db.entity.Dibs;
import gp.gameproto.db.entity.Review;
import gp.gameproto.db.entity.UserTest;
import gp.gameproto.db.entity.ReviewSummary;
import gp.gameproto.db.entity.Game;
import gp.gameproto.db.entity.GameCategory;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Test")
public class Test {
    @Id
    @Column(name = "test_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer round;

    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private LocalDateTime reviewDate;//테스트리뷰 종료 일자

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    private Integer recruitedTotal;

    @Column(nullable = false)
    private String downloadLink;

    @Column(nullable = false)
    private Character deleted;

    private String imgPath;

    private String status;

    @Column(nullable = false)
    private String gameName;

    // 생성자
    @Builder
    public Test(Integer round, String description, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime reviewDate,
                LocalDateTime createdAt, Integer recruitedTotal, String downloadLink, Character deleted, String imgPath,
                String status, String gameName){
        this.round = round;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviewDate = reviewDate;
        this.createdAt = createdAt;
        this.recruitedTotal = recruitedTotal;
        this.downloadLink = downloadLink;
        this.deleted = deleted;
        this.imgPath = imgPath;
        this.status = status;
        this.gameName = gameName;
    }

    // 연관관계 매핑

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<Dibs> dibsList;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<UserTest> userTestList;

    @OneToOne(mappedBy = "test", cascade = CascadeType.REMOVE)
    private ReviewSummary reviewSummary;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game; // 이거 필요한건가??

    @OneToMany(mappedBy = "test")
    private List<GameCategory> gameCategoryList;

}
