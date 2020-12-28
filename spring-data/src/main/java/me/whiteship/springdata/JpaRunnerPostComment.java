package me.whiteship.springdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// JPA 프로그래밍 5.Cascade + JPA 프로그래밍 6.Fetch

@Component
@Transactional  //entityManager 와 관련 모든 오퍼레이션들은 한 트랜잭션안에서 일어나야 한다.
public class JpaRunnerPostComment implements ApplicationRunner {

    //entityManager 를 주입받는다.
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception{
/*

        System.out.println("===============================");
        System.out.println("post - comment 예제 ");

        Post post = new Post();
        post.setTitle("Spring Data JPA 언제 보나...");

        Comment comment = new Comment();
        comment.setComment("빨리 보고 싶어요.");
        post.addComment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("곧 보여드릴게요.");
        post.addComment(comment1);

        Session session = entityManager.unwrap(Session.class);
        //session.save(post);
        Post post2 = session.get(Post.class, 3l);
        System.out.println("==========================");
        System.out.println(post2.getTitle());

        //Cascade (전파)
        //post 엔티티가 persistent 상태로 될 때, cascade 타입에 의해서 child 인 comment 까지 같이 저장된다.
        //session.save(post);

        //cascade remove 를 이용하여 delete 도 전파 가능하다.
        Post post3 = session.get(Post.class, 1l);
        session.delete(post3);
*/

    }
}
