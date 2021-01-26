package me.whiteship.springdatacommonweb.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //스프링 데이터 JPA 7. EntityGraph

    //@EntityGraph 를 사용하면 @NamedEntityGraph 그룹에 정의되어있는 엔티티 에 대해 EAGER / LAZY 전략을 설정 가능
    //기본값은 설정한 엔티티는 EAGER. 나머지는 LAZY
    @EntityGraph(value = "Comment.post")
    String getById(Long id);
}
