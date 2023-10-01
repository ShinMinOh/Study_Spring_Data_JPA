package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

/**
 * Spring Data Jpa 상속받는 인터페이스
 * 인터페이스끼리 상속 받을때는 extends
 * */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
