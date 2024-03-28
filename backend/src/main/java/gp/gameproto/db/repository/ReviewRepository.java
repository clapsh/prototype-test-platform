package gp.gameproto.db.repository;

import gp.gameproto.db.entity.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Review;

import java.util.List;
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


    public List<Review> findAll(){
        return em.createQuery("select r from Review r" +
                        " where r.deleted = 'N'")
                .getResultList();
    }

    public Optional<List<Review>> findByTestId(Long testId) {
        Optional<List<Review>> reviews = null;
        try{
            reviews = Optional.ofNullable(em.createQuery(
                            "select r from Review r"
                                    +" where r.test.id = :testId"
                                    +" and r.deleted = 'N'"
                                    +" order by r.createdAt desc"
                            , Review.class)
                    .setParameter("testId", testId)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            reviews = Optional.empty();
        }
        return reviews;
    }

    public Optional<List<Review>> findByUserEmail(String email) {
        Optional<List<Review>> reviews = null;
        try{
            reviews = Optional.ofNullable(em.createQuery(
                            "select r from Review r"
                                    +" where r.user.email = :email"
                                    +" and r.deleted = 'N'"
                                    +" order by r.createdAt desc"
                            , Review.class)
                    .setParameter("email", email)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            reviews = Optional.empty();
        }
        return reviews;
    }

    public Optional<List<Review>> findByGameIdAll (Long gameId){
        Optional<List<Review>> reviews = null;
        try{
            reviews = Optional.ofNullable(em.createQuery(
                            "select r from Review r"
                                    +" where r.test.game.id = :gameId"
                                    +" and r.deleted = 'N'"
                                    +" order by r.createdAt desc"
                            , Review.class)
                    .setParameter("gameId", gameId)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            reviews = Optional.empty();
        }
        return reviews;
    }
    public Optional<List<Review>> findByGameIdRound(Long gameId, Integer round){
        Optional<List<Review>> reviews = null;
        try{
            reviews = Optional.ofNullable(em.createQuery(
                            "select r from Review r"
                                    +" where r.test.game.id = :gameId"
                                    +" and r.test.round = :round"
                                    +" and r.deleted = 'N'"
                                    +" order by r.createdAt desc"
                            , Review.class)
                    .setParameter("gameId", gameId)
                    .setParameter("round", round)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            reviews = Optional.empty();
        }
        return reviews;
    }

    public Long findCntByUserEmail(String email) {
        Long reviewCnt;
        try{
            reviewCnt = (em.createQuery(
                            "select count(r) from Review r"
                                    +" where r.user.email = :email"
                                    +" and r.deleted = 'N'"
                            , Long.class)
                    .setParameter("email", email)
                    .getSingleResult());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            reviewCnt = 0L;
        }
        return reviewCnt;
    }

    public Optional<List<Review>> findReviewByKeyword(Long gameId, String keyword, Integer testRound){
        Optional<List<Review>> reviews = null;
        try{
            reviews = Optional.ofNullable(em.createQuery(
                            "select r from Review r"
                                    +" where r.test.game.id = :gameId"
                                    +" and r.test.round = :round"
                                    +" and r.text like concat('%', :keyword, '%')"
                                    +" and r.deleted = 'N'"
                                    +" order by r.createdAt desc"
                            , Review.class)
                    .setParameter("gameId", gameId)
                    .setParameter("keyword", keyword)
                    .setParameter("round", testRound)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            reviews = Optional.empty();
        }
        return reviews;
    }
}
