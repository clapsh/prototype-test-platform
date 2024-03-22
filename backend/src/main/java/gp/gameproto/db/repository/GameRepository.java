package gp.gameproto.db.repository;

import gp.gameproto.db.entity.Game;
import gp.gameproto.db.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameRepository {
    private final EntityManager em;

    // 저장
    public Game save(Game game){
        if (game.getId() == null){
            em.persist(game);
        }else {
            em.merge(game);
        }
        return game;
    }

    // 이름으로 조회
    public Optional<Game> findByName (String name){
        Optional<Game> game = null;
        try{
            game = Optional.ofNullable(em.createQuery(
                    "select g from Game g where g.name = :name", Game.class)
                    .setParameter("name", name)
                    .getSingleResult());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            game = Optional.empty();
        }
        return game;
    }

    //아이디로 조회
    public Optional<Game> findById (Long id){return Optional.ofNullable(em.find(Game.class,id));}


}
