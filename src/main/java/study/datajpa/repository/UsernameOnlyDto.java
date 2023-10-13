package study.datajpa.repository;

public class UsernameOnlyDto {

  private final String username;

  public UsernameOnlyDto(String username) { //  생성자
    this.username = username;
  }

  public String getUsername() { //  getter
    return username;
  }
}
