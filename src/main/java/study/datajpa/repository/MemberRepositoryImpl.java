package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

/**
 * 사용자 정의 인터페이스 구현 클래스. 인터페이스인 MemberRepositoryCustom의 구현체
 * */
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {
    return em.createQuery("select m from Member m")
        .getResultList();
  }
}
