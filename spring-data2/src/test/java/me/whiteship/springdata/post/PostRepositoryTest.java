package me.whiteship.springdata.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest    // 슬라이싱 테스트
//@SpringBootTest   // full 테스트
@Import(PostRepositoryTestConfig.class) //테스트 실행시, 해당 클래스 임포트.
public class PostRepositoryTest {

    @Autowired  //7
    PostRepository postRepository;

    @Autowired  //8
    PostRepository2 postRepository2;

    //ApplicationContext 는 BeanFactory 도 상속받고있지만, Application Event Publisher 도 상속받고 있어서
    //이벤트 관련된 기능을 사용가능하다.
    @Autowired  //9
    ApplicationContext applicationContext;

    //9 - AbstractAggregateRoot 를 이용해서 이벤트 발생.
    @Test
    public void eventCrud(){
        Post post = new Post();
        post.setTitle("hibernate");

        assertThat(postRepository2.contains(post)).isFalse();

        //스프링 데이터는 이벤트 자동 퍼블리싱 기능을 제공한다.
        //save 할 때, DomainEvents 에 모아져있는 이벤트들을 퍼블리싱 시킨다. 그러면 우리가 만든 리스너가 실행된다.
        postRepository2.save(post.posting());

        assertThat(postRepository2.contains(post)).isTrue();

        postRepository2.delete(post);
        postRepository2.flush();
    }

    //9 - 이벤트를 수동으로 직접 던지는 예제.
    @Test
    public void event(){
        Post post = new Post();
        post.setTitle("event");
        PostPublishedEvent event = new PostPublishedEvent(post);

        applicationContext.publishEvent(event);
    }

    //8. 기본 리포지토리 커스터마이
    @Test
    public void crud2(){
        Post post = new Post();
        post.setTitle("hibernate");

        assertThat(postRepository2.contains(post)).isFalse();
        postRepository2.save(post);

        assertThat(postRepository2.contains(post)).isTrue();

        postRepository2.delete(post);
        postRepository2.flush();
    }

    //7. 커스텀 리포지토리
    @Test
    public void crud(){
        Post post = new Post();
        post.setTitle("hibernate");
        //save 는 find 함수 때문에 필요한 작업이라, 필요할 시점에 flush.
        postRepository.save(post);
        postRepository.findMyPost();

        //flush 강제하면, remove 상태를 db와 싱크한다. = 즉,  delete 쿼리를 db에 실행함.
        postRepository.delete(post);
        postRepository.flush();
    }
}