package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    //등록
    public Team save(Team team){
      em.persist(team);
      return team;
    }

    //삭제
    public void delete(Team team){
      em.remove(team);
    }

    //전체 조회
    public List<Team> findAll(){
      return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    // id로 조회
    public Optional<Team> findById(Long id) {
      Team team = em.find(Team.class, id);
      return Optional.ofNullable(team);
    }

    // 객체 count 조회
    public long count(){
      return em.createQuery("select count(t) from Team t", Long.class)
                .getSingleResult();
    }
  }
