package gp.gameproto.db.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import gp.gameproto.db.entity.Test;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestRepository {//service(여러 DAO를 호출하여 여러 데이터를 접근/갱신하며, 해당 데이터에 대한 비즈니스 로직 담당)와 db를 연결 (단일 데이터 접근/갱신)
    private final EntityManager em;

    public void saveTest(Test test){
        if (test.getId() == null){
            em.persist(test);
        }else{
            em.merge(test);
        }
    }

    public List<Test> findAll(){
        return em.createQuery("select m from Test m")
                .getResultList();
    }

    public Test findById(Long id){
        return em.find(Test.class, id);
    }
}
