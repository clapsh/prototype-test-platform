package gp.gameproto.db.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public Long save (User user){
        if (user.getId() == null){ // 신규 등록
            em.persist(user);
        }else{ // 수정
            em.merge(user); // 준영속 컨텍스트 객체를 영속성 컨텍스트가 관리하는 형태로 반환한다
        }
        return user.getId();
    }

    public Optional<List<User>> findByName (String name){
        return Optional.ofNullable(em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList());
    }
    public Optional<User> findByEmail(String email){
        Optional<User> user = null;
        try{
            user = Optional.ofNullable(em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
        }catch (NoResultException e) {
            System.out.println("### ERROR:"+e+"###");
            user = Optional.empty();
        }
        return user;
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u")
                .getResultList();
    }
}
