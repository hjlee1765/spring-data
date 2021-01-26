package me.whiteship.springdatacommonweb.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository -> 안붙여도 된다. 왜냐? JpaRepository 의 구현체인 SimpleJpaRepository 에 이미 @Repository 가 붙어있음.
//@Repository 의 주요 역할 : 하이버네이트의 sql exception 을 Spring 의 DataAccessException 으로 변환해준다.
public interface PostRepository extends JpaRepository<Post, Long> {
}


