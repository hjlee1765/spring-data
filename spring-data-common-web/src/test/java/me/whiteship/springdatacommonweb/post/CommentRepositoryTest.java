package me.whiteship.springdatacommonweb.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getComment(){
        //N:1 관계.(comment : post)
        //fetch 전략에 따른 조회 결과
        //EAGER : comment 와 Post 는 find 시 join 되서 결과나 나온다. (n:1 관계에서 디폴트는 EAGER)
        //LAZY : comment 만 먼저 조회. Post 필요할 때 LAZY 조회.

        //@EntityGraph 에 의해 EAGER 로 가져오게 됨.
        commentRepository.getById(1l);

        System.out.println("=============================");

        //ManyToOne 은 fetchType.EAGER 가 디폴트. 바꾸려면 fetch 전략 LAZY 로 바꿔줘야 함.
        //현재 @ManyToOne(fetch = FetchType.LAZY)로 설정해 놓아서 LAZY 로 가져오게 됨.
        commentRepository.findById(1l);

    }

    // 스프링 데이터 JPA 8. Projection

    @Test
    public void getCommentProjection(){
        commentRepository.findByPost_Id(1l, CommentSummary.class);
    }

    @Test
    public void getCommentProjection2(){
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("spring data jpa projection");
        comment.setPost(savedPost); // save 의 리턴값을 그대로 사용하는게 유리함.(무조건 persistent 상태라서. merger 는 복사해서 저장하기 때문.)
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        //프로젝션 인터페이스가 여러개일 경우, 제네릭 사용해서 class 타입을 넘긴다.
        commentRepository.findByPost_Id(savedPost.getId(), CommentOnly.class).forEach(x -> {
            System.out.println("===================");
            System.out.println(x.getComment());
        });
    }
}