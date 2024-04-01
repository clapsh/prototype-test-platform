package gp.gameproto.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gp.gameproto.dto.UpdateTestRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(columnDefinition = "LONGTEXT")
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

    private Integer dibsCnt;

    // 연관관계 매핑
    @JsonIgnore
    @OneToMany(mappedBy = "test")
    private List<Dibs> dibsList;

    @JsonIgnore
    @OneToMany(mappedBy = "test")
    private List<Review> reviewList;

    @JsonIgnore
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "test")
    private ReviewSummary reviewSummary;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    // 생성자
    @Builder
    public Test(Integer round, String description, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime reviewDate,
                LocalDateTime createdAt, Integer recruitedTotal, String downloadLink, Character deleted, String imgPath,
                String status){
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
        this.dibsCnt = 0;
    }

    // User와 연관관계 매핑
    public void mappingUser(User user){
        this.user = user;
        user.addTest(this);// -> 필요?
    }
    // Game와 연관관계 매핑
    public void mappingGame(Game game){
        this.game = game;
    }
    // 테스트 정보 수정
    public Test update(UpdateTestRequest request){
        this.round = request.getRound();
        this.description = request.getDescription();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.reviewDate = request.getReviewDate();
        this.modifiedAt = LocalDateTime.now();
        this.recruitedTotal = request.getRecruitedTotal();
        this.downloadLink = request.getDownloadLink();
        this.imgPath = request.getImgPath();
        return this;
    }

    // review list에 추가하는 메서드
    public void addReview(Review review){
        this.reviewList.add(review);
    }

    // dibs list에 추가하는 메서드
    public void addDibs(Dibs dibs){
        this.dibsList.add(dibs);
        this.dibsCnt = this.dibsList.size();
    }

    // dibs list에 삭제하는 메서드
    public boolean deleteDibs(Dibs dibs){
        if(this.dibsList.remove(dibs)){
            this.dibsCnt = this.dibsList.size();
            return true;
        }
        return false;
    }
    // test 삭제
    public void delete(){
        this.deleted = 'Y';
    }
}
