package me.whiteship.springdatacommonweb.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //스프링 데이터 JPA 7. EntityGraph

    //@EntityGraph 를 사용하면 @NamedEntityGraph 그룹에 정의되어있는 엔티티 에 대해 EAGER / LAZY 전략을 설정 가능
    //기본값은 설정한 엔티티는 EAGER. 나머지는 LAZY
    @EntityGraph(value = "Comment.post")
    String getById(Long id);

    // 스프링 데이터 JPA 8. Projection
    // 쿼리메서도 사용할 때, 필요한 컬럼만 쿼리하기 = 쿼리 최적화.
    // 인터페이스 기반 프로젝션 - closed 프로젝션
    //List<CommentSummary> findByPost_Id(Long id);

    //문제 : 프로젝션 인터페이스가 여러개일경우 오버로딩이 안된다.
    //List<CommentOnly> findByPost_Id(Long id);

    //해결 :  제네릭을 사용하면 된다.
    <T> List<T> findByPost_Id(Long id, Class<T> type);
}
