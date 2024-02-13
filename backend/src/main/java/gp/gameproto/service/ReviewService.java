package gp.gameproto.service;
import gp.gameproto.db.repository.ReviewRepository;
import gp.gameproto.db.entity.Review;
import gp.gameproto.dto.AddReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // 빈으로 등록
public class ReviewService {
    private final ReviewRepository reviewRepository;
    @Transactional
    public Long save(AddReviewRequest request){ // request를 받아서 리뷰 DB에 저장하는 메서드
        return reviewRepository.saveReview(request.toEntity());
    }
}
