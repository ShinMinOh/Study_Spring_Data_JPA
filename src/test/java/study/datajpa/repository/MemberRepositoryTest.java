package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Optional;
import study.datajpa.entity.Team;

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
    @Autowired TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get(); //Optional 타입을 .get()으로 받아 Member타입으로 받기


        /**
         * 검증
         * */
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //전체 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deleted = memberRepository.count();
        assertThat(deleted).isEqualTo(0);
    }


    @Test
    public void findByUsernameGreaterThen(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }


    // Named Query 테스트코드
    @Test
    public void testNamedQuery(){                                            //MemberRepository 2번 test code
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");       //MemberRepository에 있는 findByUsername 가 실행됨.
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void testQuery(){                                                //MemberRepository 3번 test code
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 10);

        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void findUsernameList() {                                                //MemberRepository 4번 값조회 test code
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();

        // 값 조회
        for(String s : usernameList){
            System.out.println("s = "+s);
        }
        assertThat(usernameList.get(0)).isEqualTo(m1.getUsername());
        assertThat(usernameList.get(1)).isEqualTo(m2.getUsername());
    }


    @Test
    public void findMemberDto() {                                                //MemberRepository 4번 dto 조회 test code
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        // 값 조회
        for(MemberDto dto : memberDto){
            System.out.println("dto = "+dto);
        }
       /* System.out.println(memberDto.get(0).getUserName()); //AAA
        System.out.println(memberDto.get(0).getTeamName()); //teaA
        System.out.println(m1.getUsername());               //AAA
        System.out.println(m1.getTeam().getName());         //teamA
       */
        assertThat(memberDto.get(0).getUserName()).isEqualTo(m1.getUsername());
        assertThat(memberDto.get(0).getTeamName()).isEqualTo(m1.getTeam().getName());
    }

    @Test
    public void findByNames() {                                                //MemberRepository 5번 test code
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for(Member member : result){
            System.out.println("member = "+member);
        }
    }

    @Test
    public void returnType() {                                                //MemberRepository 6번 test code
        Member m0 = new Member("A", 10);
        Member m0_1 = new Member("A", 30);
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m0);
        memberRepository.save(m0_1);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> findListMember = memberRepository.findListByUsername("A"); //컬렉션
        for(Member member : findListMember){
            System.out.println("memberLIst = "+member);
        }

        Member findMember = memberRepository.findMemberByUsername("AAA");          //단건
        System.out.println("findMember = "+findMember);
    }


    @Test
    public void paging(){                                                           //MemberRepository 7번 test code
        //given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age = 10;

        //when
        //Spring Data Jpa는 페이지를 1부터가 아닌 0부터 시작함.
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //Page 이점: totalcount 쿼리까지 같이 날려줌
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //위의 page의 경우 반환타입이 엔티티(Member)라 api에 반환할때 그대로 노출됨(위험)
        //따라서 .map을 통해 MemberDto로 변환해서 반환하기 꼭!
        Page<MemberDto> toMap = page.map(
            member -> new MemberDto(member.getId(), member.getUsername(), null));

        //then
        List<Member> content = page.getContent();   //getContent() : 실제 3개의 컨텐츠내용 꺼내옴.
        long totalElements = page.getTotalElements();    //getTotalCount와 동일

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = "+totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2);  //총 페이지 개수
        assertThat(page.isFirst()).isTrue(); //당연히 첫번째 페이지니까 True
        assertThat(page.hasNext()).isTrue();//다음 페이지가 있는가
    }

    @Test
    public void bulkUpdate(){                                                       //MemberRepository 8번 test code
        //given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",19));
        memberRepository.save(new Member("member3",20));
        memberRepository.save(new Member("member4",21));
        memberRepository.save(new Member("member5",40));

        //when
        int resultCount = memberRepository.bulkAgePlus(20);

        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0);


        System.out.println("result = "+result);
        System.out.println("member5 = "+member5);

        //then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){                                                    //MemberRepository 9,10번 test code
        //given
        //member1 -> teamA
        //member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member1", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //when   N+1
        //select Member -> 1
        //List<Member> members = memberRepository.findMemberFetchJoin();    //9번
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");       //10번

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println(

                "member.teamClass = "+ member.getTeam().getClass());
            System.out.println("member.team = "+ member.getTeam().getName());
        }
    }

    @Test
    public void queryHint(){
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(member1.getId()).get();
        findMember.setUsername("member2");
    }

    @Test
    public void callCustom(){
        List<Member> result = memberRepository.findMemberCustom();
    }
}