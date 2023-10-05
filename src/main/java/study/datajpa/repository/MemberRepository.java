package study.datajpa.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

/**
 * Spring Data Jpa 상속받는 인터페이스
 * 인터페이스끼리 상속 받을때는 extends
 * 주로 1,3번을 많이 사용.
 * 1번: 간단한 쿼리 사용할때
 * 3번: 복잡한 쿼리 사용할때
 * Querydsl: 동적쿼리가 필요할때
 * */
public interface    MemberRepository extends JpaRepository<Member, Long> {

    //1. 쿼리 메소드: 메소드 이름으로 쿼리 생성.
    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);


    //2. Named Query, 파라미터 바인딩(@Param)
    //@Query(name = "Member.findByUsername")                                                //name = 을 보고 NamedQuery를 찾아서 실행시킴. 이 부분은 주석처리해도 실행됨--> JpaRepository<Member, > 에서 명시한 Member타입+아래 findByUsername(메서드명)을 먼저 찾음.
    List<Member> findByUsername(@Param("username") String username);                        //@Param : JPQL에 명시된 네임드 파라미터(:username)를 넘겨줘야할때 사용.


    //3. @Query - 레포지토리 메소드에 쿼리 정의하기, 파라미터 바인딩(@Param)
    @Query("select m from Member m where m.username = :username and m.age = :age")         //JPQL을 바로 명시해서 쓸수있고 첫번째 예시와 반대로 findUser라고 간단하게 정의할 수 있는게 장점.
    List<Member> findUser(@Param("username") String username, @Param("age") int age);


    //4. @Query - 엔티티가 아닌 값 조회하기
    @Query("select m.username from Member m")
    List<String> findUsernameList();                                                       //name이 String타입이기때문에 String 타입으로 정의


    //4. @Query, 엔티티가 아닌 DTO 조회하기
    // Member의 id,username Team의 name을 가지고와서 MemberDto의 생성자에게 전달해서 MemberDto로 만들어서 반환
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")   //Dto 조회할때는 new 오퍼레이션 꼭 써줘야함. teamName도 갖고와야하기때문에 join
    List<MemberDto> findMemberDto();


    //5. 컬렉션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);                             //List보다 상위 클래스인 Collection으로 받으면 더 많은 종류의 데이터 받을 수 잇음.
}

