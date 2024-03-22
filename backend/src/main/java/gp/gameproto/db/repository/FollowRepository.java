package gp.gameproto.db.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Follow;
@Repository
@RequiredArgsConstructor
public class FollowRepository {
    private final EntityManager em;

    public void saveFollow(Follow follow){
        if(follow.getId() == null){
            em.persist(follow);
        }else {
            em.merge(follow);
        }
    }

    public Follow findById(Long id){
        return em.find(Follow.class, id);
    }
}
