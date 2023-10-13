package study.datajpa.repository;

import jakarta.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
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
 * MemberRepositoryCustom : 따로 만든 사용자 정의 인터페이스 상속
 * */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

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


    //6. 반환타입 - 여러가지 반환타입 지원
    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username);     //단건
    Optional<Member> findOptionalByUsername(String username); //단건을 Optional로 감싸서 반환

    //7. Spring Data Jpa - 페이징과 정렬
    //Page 반환타입, 메서드 이름으로 쿼리(1번)
    //파라미터로 Pageable 넘기고 반환타입을 Page로 하면 pageable위치에 페이징 조건이 들어감.
    Page<Member> findByAge(int age, Pageable pageable);

    //8. 벌크성 수정쿼리
    @Modifying(clearAutomatically = true)                  // @Query 어노테이션을 통해 DML문 실행할 경우 반드시 붙이기.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    //9. 페치조인
    @Query("select m from Member m left join fetch m.team") // Member를 조회할때 연관된 Team을 같이 한방쿼리로 가지고옴.
    List<Member> findMemberFetchJoin();

    //10. 엔티티 그래프
    // 페치조인 방식을 쿼리문을 쓰지 않고 Spring data Jpa처럼 이름만으로 쓰고자 할때
    @Override
    @EntityGraph(attributePaths = {"team"})     //team 엔티티까지 같이 가져오기
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})     //team 엔티티까지 같이 가져오기
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})     //team 엔티티까지 같이 가져오기
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    // 11. 쿼리 힌트(변경감지(update) 적용X)
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    // 12. Projections
    List<UsernameOnly> findProjectionsByUsername(@Param("username") String username);                        //@Param : JPQL에 명시된 네임드 파라미터(:username)를 넘겨줘야할때 사용.
        //반환 타입: UsernameOnly 인터페이스

}

