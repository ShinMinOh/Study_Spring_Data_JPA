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


    public List<Member> findByUsernameGreaterThen(String username, int age){
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
            .setParameter("username", username)
            .setParameter("age",age)
            .getResultList();
    }

    public List<Member> findByUsername(String username){
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }


    //순수 JPA를 이용한 페이징과 정렬
    public List<Member> findByPage(int age, int offset, int limit){  //나이:검색조건, 몇번째부터 시작해서 몇개를 가지고 올건지
      return   em.createQuery("select m from Member m where m.age = :age order by m.username desc")
            .setParameter("age", age)
            .setFirstResult(offset)   //어디서부터 가져올건지
            .setMaxResults(limit)     //몇개를 가져올건지
            .getResultList();
    }

    //페이징 쿼리를 짤때, 현재 내 페이지가 몇번째 페이지인지 알려주기 위해 totalcount를 만듦.
    // totalCount에는 단순하게 count만 필요로 하기 때문에 sorting이 들어갈 필요가 없어 성능최적화를 위해 제외함.
    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
            .setParameter("age",age)
            .getSingleResult(); //count 하나값을 가져오기때문에 SingleResult로 받음.
    }

    //순수 JPA를 이용한 벌크 연산
    public int bulkAgePlus(int age){
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
                .setParameter("age",age)
                .executeUpdate();
    }


}
