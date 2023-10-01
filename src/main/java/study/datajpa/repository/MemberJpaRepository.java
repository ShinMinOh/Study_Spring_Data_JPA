package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

/**
 * Spring Data Jpa 사용 안하는 레포지토리
 * /

/**
 * CRUD 중 하나인 Update는 필요가 없다
 * ->em을 통해서 조회 해온 후, entity를 직접 수정하고 트랜잭션을 commit하면 자동으로 변경된것을 감지하여 DB에 update 쿼리를 날려준다.
 * */
@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){      // 저장
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

    // 전체조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                    .getResultList();
    }

    // id로 조회
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    // 카운트
    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                 .getSingleResult(); //count 하면 Long타입으로 반환됨.
    }

    // 단건조회
    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
