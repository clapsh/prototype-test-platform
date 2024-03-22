package gp.gameproto.db.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Dibs;
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
}
