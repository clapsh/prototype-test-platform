package gp.gameproto.db.repository;

import gp.gameproto.db.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TestRepository {//service(여러 DAO를 호출하여 여러 데이터를 접근/갱신하며, 해당 데이터에 대한 비즈니스 로직 담당)와 db를 연결 (단일 데이터 접근/갱신)
    private final EntityManager em;

    public Test saveTest(Test test){
        if (test.getId() == null){
            em.persist(test);
        }else{
            em.merge(test);
        }
        return test;
    }

    public List<Test> findAll(){
        return em.createQuery("select m from Test m"
                        +" where t.deleted = 'N'")
                .getResultList();
    }

    public Optional<Test> findById(Long id){
        Optional<Test> test = null;
        try {
            test = Optional.ofNullable(em.find(Test.class, id));
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            test = Optional.empty();
        }
        return test;
    }

    public Optional<Test> findByNameAndRound(String gameName, Integer round){
        Optional<Test> test = null;
        try {
            test = Optional.ofNullable(em.createQuery(
                    "select t from Test t where t.game.name = :gameName and t.round = :round and t.deleted = 'N'", Test.class)
                            .setParameter("gameName", gameName)
                            .setParameter("round", round)
                            .getSingleResult());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            test = Optional.empty();
        }
        return test;
    }

    public Optional<List<Test>> findUserRecentGameList(String email){
        Optional<List<Test>> tests = null;
        try{
            tests = Optional.ofNullable(em.createQuery(
               "select t from Test t"
                       +" where t.user.email = :email"
                       +" and t.deleted = 'N'"
                       +" group by t.game.name, t"
                       +" order by t.game.createdAt desc"
                       , Test.class)
                        .setParameter("email", email)
                        .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            tests = Optional.empty();
        }
        return tests;
    }

    public Optional<List<Test>> findRecentGameList(){
        Optional<List<Test>> tests = null;
        try {
            tests = Optional.ofNullable(em.createQuery(
                            "select t from Test t"
                                    +" where t.deleted = 'N'"
                                    +" group by t.game.name, t"
                                    +" order by t.game.createdAt desc"
                            , Test.class)
                            .setMaxResults(5)
                            .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            tests = Optional.empty();
        }
        return tests;
    }

    public Optional<List<Test>> findCategorizedGameList(Category category){
        Optional<List<Test>> tests = null;
        try {
            // 테스트 종료 일자가 오늘을 지나지 않은 것
            tests = Optional.ofNullable(em.createQuery(
                            "select t from Test t"
                                    +" where t.endDate >= :now"
                                    +" and t.game.category = :category"
                                    +" and t.deleted = 'N'"
                                    //+" group by t.game.name, t"
                                    // 페이징 적용??
                                    +" order by t.createdAt desc"
                            , Test.class)
                            .setParameter("now", LocalDateTime.now())
                            .setParameter("category", category)
                            .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            tests = Optional.empty();
        }
        return tests;
    }

    public Optional<List<Test>> findUserGameList(String email){
        Optional<List<Test>> tests = null;
        try{
            tests = Optional.ofNullable(em.createQuery(
                            "select t from Test t"
                                    +" where t.user.email = :email"
                                    +" and t.deleted = 'N'"
                                    +" order by t.createdAt desc"
                            , Test.class)
                    .setParameter("email", email)
                    .getResultList());
        }catch (NoResultException e){
            System.out.println("### ERROR:"+e+"###");
            tests = Optional.empty();
        }
        return tests;
    }


}
