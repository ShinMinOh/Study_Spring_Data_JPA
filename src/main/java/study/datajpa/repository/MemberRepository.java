package study.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

/**
 * Spring Data Jpa 상속받는 인터페이스
 * 인터페이스끼리 상속 받을때는 extends
 * */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);   // 쿼리 메소드.메소드 이름으로 쿼리 생성.

    /* Named Query
    //@Query(name = "Member.findByUsername")                                  //name = 을 보고 NamedQuery를 찾아서 실행시킴. 이 부분은 주석처리해도 실행됨--> JpaRepository<Member, > 에서 명시한 Member타입+아래 findByUsername(메서드명)을 먼저 찾음.
    List<Member> findByUsername(@Param("username") String username);        //@Param : JPQL에 명시된 네임드 파라미터(:username)를 넘겨줘야할때 사용.
    */

}

