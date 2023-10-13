package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

/**
 * Projections 학습 위한 인터페이스
 * 전체 엔티티가 아닌 회원 이름만 딱 조회.
 * */

public interface UsernameOnly {

  @Value("#{target.username + ' ' + target.age}")   //Open Projection
  String getUsername();
  //조회할 엔티티의 필드를 getter 형식으로 지정하면 해당 필드만 선택해서 조회(Projection)

}
