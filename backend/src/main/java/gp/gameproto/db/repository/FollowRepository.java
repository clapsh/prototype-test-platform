package gp.gameproto.db.repository;

import gp.gameproto.db.entity.Dibs;
import gp.gameproto.db.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Follow;

import java.util.Optional;

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
/*
    public Optional<Follow> findByFollowerAndFollowing(User follower, User following){
        Optional<Follow> follow = null;
        try{
            follow = Optional.ofNullable(em.createQuery(
                            "select f from Follow f"
                                    +" where f.follower = :follower"
                                    +" and f.following = :following", Follow.class)
                    .setParameter("follower", follower)
                    .setParameter("following", following)
                    .getSingleResult());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            follow = Optional.empty();
        }
        return follow;
    }

    public Long deleteByFollowerAndFollowing(User follower, User following){
        Optional<Follow> follow = Optional.ofNullable(em.createQuery(
                        "select f from Follow f"
                                +" where f.follower = :follower"
                                +" and f.following = :following", Follow.class)
                .setParameter("follower", follower)
                .setParameter("following", following)
                .getSingleResult());
        Long followId = follow.get().getId();
        em.remove(em.contains(follow)? follow : em.merge(follow));

        return followId;
    }*/

}
