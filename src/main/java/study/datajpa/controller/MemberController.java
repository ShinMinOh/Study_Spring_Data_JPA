package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @GetMapping("/members/{id}")
  public String findMember(@PathVariable("id") Long id){
    Member member = memberRepository.findById(id).get();
    return member.getUsername();
  }

  @GetMapping("/members2/{id}")
  public String findMember2(@PathVariable("id") Member member){     //도메인 클래스 컨버터 : HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩
    return member.getUsername();
  }

  @GetMapping("/members")
  public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username") Pageable pageable){ //꼭 DTO로 반환.
      Page<Member> page = memberRepository.findAll(pageable);
    Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));  //map을 이용해서 Member엔티티 MemberDto로 변환해서 반환.
    return map;
  }



  //테스트 데이터 생성
  @PostConstruct      // Spring Application 올라올때, 한번 실행.
  public void init(){
    //memberRepository.save(new Member("userA"));
    for( int i =0; i<100; i++){
      memberRepository.save(new Member("user" + i , i ));
    }
  }
}
