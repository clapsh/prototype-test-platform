package gp.gameproto.service;
import gp.gameproto.db.entity.Game;
import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.ReviewRepository;
import gp.gameproto.db.entity.Review;
import gp.gameproto.db.repository.TestRepository;
import gp.gameproto.db.repository.UserRepository;
import gp.gameproto.dto.AddReviewRequest;
import gp.gameproto.dto.ReviewResponse;
import gp.gameproto.dto.UpdateReviewRequest;
import gp.gameproto.dto.UpdateTestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service // 빈으로 등록
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    @Transactional
    public Long save(AddReviewRequest request, Long testId, String email){ // request를 받아서 리뷰 DB에 저장하는 메서드
        //사용자가 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 테스트가 존재하는지 확인
        Test test = testRepository.findById(testId)
                .orElseThrow(()-> new UsernameNotFoundException("테스트가 존재하지 않습니다."));

        Review review = request.toEntity();
        // 연관관계 매핑
        review.mappingUser(user);
        review.mappingTest(test);
        test.addReview(review);

        return reviewRepository.saveReview(review);
    }

    // 테스트 별 리뷰 리스트 가져오기
    @Transactional(readOnly = true)
    public List<Review> findReviewsOfTest(Long testId){
        List<Review> reviews = reviewRepository.findByTestId(testId)
                .orElseThrow(()-> new IllegalArgumentException("not found"));
        return reviews;
    }

    // 유저별 리뷰 리스트 가져오기
    @Transactional(readOnly = true)
    public List<Review> findReviewsOfUser(String email){
        List<Review> reviews = reviewRepository.findByUserEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("not found"));
        return reviews;
    }

    @Transactional
    public Review update(UpdateReviewRequest request, String email, Long reviewId) {
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ reviewId));

        // 리뷰 수정 권한이 없는 경우
        if (review.getUser().getEmail() != email){ // 예외처리??
            new UsernameNotFoundException("수정 권한이 없습니다.");
        }

        return review.update(request);
    }

    @Transactional
    public String delete(String email, Long reviewId){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ reviewId));

        // 리뷰 삭제 권한이 없는 경우
        if (review.getUser().getEmail() != email){ // 예외처리??
            new UsernameNotFoundException("삭제 권한이 없습니다.");
        }

        // 리뷰 삭제
        review.delete();
        return "삭제가 완료되었습니다.";
    }
}