package me.whiteship.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

//스프링 데이터 JPA 활용 > 스프링 데이터 Common 4. 쿼리만들기.

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //spring data jpa 는 아래 3가지 방법 을 선택해서 메소드이름으로 쿼리를 만들어 준다.
    //@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)

    // 전략
    // 1. 메소드 이름을 분석해서 쿼리 만들기 (CREATE)
    // 2. 미리 정의해 둔 쿼리 찾아 사용하기. (USE_DECLARED_QUERY)
    //    - 기본값 : jpql, 네이티브 쿼리 사용가능.
    // 3. 미리 정의한 쿼리를 찾아보고, 없으면 만들기. (CREATE_IF_NOT_FOUND) (기본값)

    //쿼리 만드는 방법 -> docs 참조.
    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likeCount);
    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);
    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    //Async
    //해당 메서드 실행을 별도의 백그라운드 스레드에 위임한다.
    //SpringBootAppliaction 파일에 @EnableAsync 붙여줘야 한다.
/*    @Async
    Future<List<Comment>> findByCommentContains(String keyword, Pageable pageable);*/

    @Async
    ListenableFuture<List<Comment>> findByCommentContains(String keyword, Pageable pageable);

    Page<Comment> findByLikeCountGreaterThanAndPost(int likeCount, Post post, PageRequest pageRequest);

    //@Query 어노테이션이 붙었으므로, 3번전략에 의해 @Query 적용
    @Query(value = "SELECT c FROM Comment AS c", nativeQuery = true)
    List<Comment> findByTitleContains2(String keyword);

}
