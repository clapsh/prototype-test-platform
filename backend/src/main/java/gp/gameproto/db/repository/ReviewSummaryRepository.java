package gp.gameproto.db.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.ReviewSummary;

@Repository
@RequiredArgsConstructor
public class ReviewSummaryRepository {
    private final EntityManager em;

    public void saveReviewSummary(ReviewSummary reviewSummary){
        if(reviewSummary.getId() == null){
            em.persist(reviewSummary);
        }else{
            em.merge(reviewSummary);
        }
    }

    public ReviewSummary findById(Long id){
        return em.find(ReviewSummary.class, id);
    }
}
