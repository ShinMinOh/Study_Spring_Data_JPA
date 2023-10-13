package study.datajpa.repository;

/**
 * 멤버와 팀 중첩으로 조회
 * */
public interface NestedClosedProjections {

  String getUsername();
  TeamInfo getTeam();    //중첩구조에서는 위에 첫줄만 최적화되서 username만 뽑아서 가져올수 있지만 그 뒤에는 엔티티를 통째로 들고옴.

  interface TeamInfo{
    String getName();  //team의 이름
  }
}
