package study.datajpa.dto;

import lombok.Data;

@Data
public class MemberDto {    //조회하고 싶은 대상이 id,username,teamname

  private Long id;
  private String userName;
  private String teamName;

  public MemberDto(Long id, String userName, String teamName) {
    this.id = id;
    this.userName = userName;
    this.teamName = teamName;
  }
}
