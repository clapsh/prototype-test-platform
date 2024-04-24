package gp.gameproto.db.repository;

import gp.gameproto.db.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.ReviewSummary;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewSummaryRepository {
    private final EntityManager em;

    public Long saveReviewSummary(ReviewSummary reviewSummary){
        if(reviewSummary.getId() == null){
            em.persist(reviewSummary);
        }else{
            em.merge(reviewSummary);
        }
        return reviewSummary.getId();
    }

    public ReviewSummary findById(Long id){
        return em.find(ReviewSummary.class, id);
    }

    public Optional<ReviewSummary> findByTestId(Long testId) {
        Optional<ReviewSummary> summary = null;
        try{
            summary = Optional.ofNullable(em.createQuery(
                            "select s from ReviewSummary s"
                                    +" where s.test.id = :testId"
                            , ReviewSummary.class)
                    .setParameter("testId", testId)
                    .getSingleResult());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            summary = Optional.empty();
        }
        return summary;
    }
}
