package study.datajpa.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)   // 이 어노테이션이 있으면 기본 생성자 안써줘도 됨.
@ToString(of = {"id", "username", "age"})           // Team 같은 연관관계 필드는 ToString에 포함하지 않기. 타고 가서 다 출력될수 있음.
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;



    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    // 연관관계 편의 메서드. 양방향 연관관계 처리.
    public void changeTeam(Team team){ //Team은 연관관계를 맺고 있는 객체이기 때문에 team뿐만 아니라 member도 바꿔줘야함.
        this.team = team;
        team.getMembers().add(this);
    }

}
