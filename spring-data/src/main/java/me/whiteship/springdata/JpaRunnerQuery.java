package me.whiteship.springdata;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


// JPA 프로그래밍 7.쿼리


@Component
@Transactional  //entityManager 와 관련 모든 오퍼레이션들은 한 트랜잭션안에서 일어나야 한다.
public class JpaRunnerQuery implements ApplicationRunner {


    //entityManager 를 주입받는다.
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception{
/*
        //JPQL
        //From 에는 엔티티 기준으로 작성한다.(엔티티이름이다 테이블 X)
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
        List<Post> posts = query.getResultList();
        posts.forEach(System.out::println);


        //native Query
        List<Post> posts2 = entityManager.createNativeQuery("Select * from Post", Post.class)
                .getResultList();
        posts2.forEach(System.out::println);*/
    }
}
