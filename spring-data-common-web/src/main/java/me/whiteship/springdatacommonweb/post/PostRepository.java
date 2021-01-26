package me.whiteship.springdatacommonweb.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository -> 안붙여도 된다. 왜냐? JpaRepository 의 구현체인 SimpleJpaRepository 에 이미 @Repository 가 붙어있음.
//@Repository 의 주요 역할 : 하이버네이트의 sql exception 을 Spring 의 DataAccessException 으로 변환해준다.
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleStartingWith(String title);

    List<Post> findByTitle(String title);

    //엔티티 위에 @NamedQuery 로 번거롭게 쓰는것 대신
    //Repository 의 메서드 위에 @Query 어노테이션을 붙여 간단히 jpql 이나 native query 수행가능.
    //Sort
    //쿼리 결과를 소팅 하고 싶을 때, Sort 만 파라미터로 추가하면 됨.
    @Query("SELECT p FROM Post AS p WHERE p.title = ?1")
    List<Post> findByTitleQueryAnnotaion(String title, Sort sort);

    //NamedParameter
    //@Query 시, 조건 참조할 때 숫서대로 1,2,3 채번하는게 아니라 이름으로 참조 가능. @Param 추가해야함.
    @Query("SELECT p FROM Post AS p WHERE p.title = :title")
    List<Post> findByTitleQueryNamedParameter(@Param("title") String title, Sort sort);


    //SpEL (스프링 표현 언어)
    //엔티티 이름을 직접 적지 않고, 알아서 엔티티 이름이 entityName 에 들어와서 변수를 사용가능.
    @Query("SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
    List<Post> findByTitleQuerySpEL(@Param("title") String title, Sort sort);

    //스프링 데이터 JPA 6. Update 쿼리.
    //업데이트 쿼리 find,count,delete 와 같이 쿼리메서드로 만들 수 없다.
    //왜? persistent 상태로 관리되다 객체 상태가 변경되면 하이버네이트가 적절한 시점에 db에 flush 하기 때문에.
    //그래서 굳이 정의 할 필요가...
    //clearAutomatically : 업데이트 쿼리 후에, persistence context 를 클리어해준다.
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p Set p.title = ?1 WHERE p.id = ?2")
    int updateTitle(String hibernate, Long id);

}


