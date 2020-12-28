package me.whiteship.springdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


// 스프링 데이터 JPA 활용 > 스프링 데이터 Common 1. 리포지토리
// 스프링 데이터 JPA 활용 > 스프링 데이터 Common 3. null 처리

//h2를 사용함
@ExtendWith(SpringExtension.class)  // = RunWith
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;


    //@DataJpaTest 에 트랜잭션 어노테이션이 있고, 스프링 프레임워크에 의해 테스트 진행하고 롤백한다.
    //하이버네이트는 어짜피 롤백한 쿼리일 걸 알아서 insert 쿼리가 날라가지 않는다.
    @Test
    @Rollback(false)
    public void crudRepository() {
        // Given
        Post post = new Post();
        post.setTitle("hello spring boot common");
        assertThat(post.getId()).isNull();

        // When
        Post newPost = postRepository.save(post);

        // Then
        assertThat(newPost.getId()).isNotNull();

        // When
        List<Post> posts = postRepository.findAll();

        // Then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(newPost);

        // When (페이지 사용)
        Page<Post> page = postRepository.findAll(PageRequest.of(0,10));

        // Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // when
        page = postRepository.findByTitleContains("spring", PageRequest.of(0,10));

        // Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // Optional 사용
        // 딘일 엔티티는 Optional , collection 타입은 null 을 리턴하지 않고, 비어있는 collection 을 리턴한다.(empty 로 비교)
        Optional<Post> byId = postRepository.findById(100L);
        assertThat(byId).isEmpty();
        //Post orElsePost = byId.orElseThrow(() -> new IllegalArgumentException());
    }

}