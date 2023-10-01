package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
/**
 * JpaRepository(Spring Data Jpa)를 상속받는 MemberRepository 이용하여 간단하게 저장, 조회 구현
 * */
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get(); //Optional 타입을 .get()으로 받아 Member타입으로 받기
      /*Optional<Member> byId = memberRepository.findById(savedMember.getId()); //있을지 없을지 모르므로 타입이 Optional
        Member member1 = byId.get();*/

        /**
         * 검증
         * */
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
}