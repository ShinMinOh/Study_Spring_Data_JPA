package study.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;

  @Getter
  @MappedSuperclass//진짜 상속관계는 아니고 속성(데이터)들만 내려서 테이블에서 가져와서 쓸수있는 어노테이션
  public class JpaBaseEntity {

    @Column(updatable = false)              //createdDate는 update되지 않도록 설정
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist(){
      LocalDateTime now = LocalDateTime.now();
      createdDate = now;
      updatedDate = now;
    }

    @PreUpdate
    public void preUpdate(){
      updatedDate = LocalDateTime.now();
    }

  }
