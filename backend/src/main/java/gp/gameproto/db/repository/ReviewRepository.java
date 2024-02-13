package gp.gameproto.db.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Review;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public Long saveReview(Review review){
        if (review.getId() == null){
            em.persist(review);
        }else {
            em.merge(review);
        }
        return review.getId();
    }

    public Optional<Review> findById(Long id){
        return Optional.ofNullable(em.find(Review.class,id));
    }

}
