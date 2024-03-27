package gp.gameproto.db.repository;

import gp.gameproto.db.entity.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Dibs;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DibsRepository {
    private final EntityManager em;

    public void save(Dibs dibs){
        if(dibs.getId() == null){
            em.persist(dibs);
        }else{
            em.merge(dibs);
        }
    }

    public Dibs findById(Long id){
        return em.find(Dibs.class,id);
    }

    public Optional<Dibs> findByUserAndTest(String email, Long testId){
        Optional<Dibs> dibs = null;
        try{
            dibs = Optional.ofNullable(em.createQuery(
                            "select d from Dibs d"
                            +" where d.user.email = :email"
                            +" and d.test.id = :testId", Dibs.class)
                            .setParameter("email", email)
                            .setParameter("testId", testId)
                        .getSingleResult());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            dibs = Optional.empty();
        }
        return dibs;
    }

    public Optional<List<Dibs>> findByUserEmail(String email){
        Optional<List<Dibs>> dibsList = null;
        try{
            dibsList = Optional.ofNullable(em.createQuery(
                            "select d from Dibs d"
                                    +" where d.user.email = :email", Dibs.class)
                    .setParameter("email", email)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            dibsList = Optional.empty();
        }
        return dibsList;
    }

    public void delete(Dibs dibs){
        em.remove(em.contains(dibs)? dibs : em.merge(dibs));
    }
}
