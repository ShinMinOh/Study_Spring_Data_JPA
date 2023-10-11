package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

  @PostConstruct
  public void init(){
    memberRepository.save(new Member("userA"));
  }
}
