package me.whiteship.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

// 스프링 데이터 Common 8. 기본 리포지토리 커스터마이징.
// 목적 : 모든 레포지토리(모든 엔티티)에 공통적으로 추가하고 싶은 기능이 있을 경우.

//중간에 있는 인터페이스는 NoRepositoryBean 넣어줘야함.
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    boolean contains(T entity);

}
