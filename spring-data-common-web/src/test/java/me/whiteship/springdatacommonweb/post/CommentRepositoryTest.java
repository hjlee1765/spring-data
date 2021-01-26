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
}