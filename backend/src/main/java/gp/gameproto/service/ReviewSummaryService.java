package gp.gameproto.service;

import gp.gameproto.db.entity.ReviewSummary;
import gp.gameproto.db.entity.Test;
import gp.gameproto.db.repository.ReviewRepository;
import gp.gameproto.db.repository.ReviewSummaryRepository;
import gp.gameproto.db.repository.TestRepository;
import gp.gameproto.dto.AddReviewSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final ReviewRepository reviewRepository;
    private final TestRepository testRepository;
    @Transactional
    public Long save(ReviewSummary reviewSummary, Long testId){ // request를 받아서 리뷰 DB에 저장하는 메서드
        // 테스트가 존재하는지 확인
        Test test = testRepository.findById(testId)
                .orElseThrow(()-> new UsernameNotFoundException("테스트가 존재하지 않습니다."));

        ReviewSummary summary = reviewSummary;
        // 연관관계 매핑
        summary.mappingTest(test);
        test.mappingReviewSummary(summary);

        return reviewSummaryRepository.saveReviewSummary(summary);
    }

    //test id로 찾기
    @Transactional(readOnly = true)
    public Optional<ReviewSummary> findByTestId(Long testId){
        Optional<ReviewSummary> summary = reviewSummaryRepository.findByTestId(testId);
        /*ReviewSummary reviewSummary;

        reviewSummary = summary.orElseThrow(()->new IllegalArgumentException("리뷰 요약 오류 발생"));*/
        return summary;
    }

}
