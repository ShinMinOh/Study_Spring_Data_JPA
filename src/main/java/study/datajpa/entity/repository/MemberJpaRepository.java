package study.datajpa.entity.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){      // 저장
        em.persist(member);
        return member;
    }

    public Member find(Long id){            // 조회
        return em.find(Member.class, id);
    }

}
