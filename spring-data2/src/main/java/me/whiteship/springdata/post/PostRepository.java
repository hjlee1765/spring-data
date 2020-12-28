package me.whiteship.springdata.post;

import me.whiteship.springdata.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

// 스프링 데이터 Common 7. 커스텀 리포지토리
//인터페이스는 다중상속 가능하다. 내가 만든 custom repo 추가.
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
}