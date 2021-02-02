package me.whiteship.springdatacommonweb.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @PersistenceContext
    private EntityManager entityManager;

    // 스프링 데이터 JPA:1. JpaRepository
    //@Repository 어노테이션의 역할 중 하나.
    //하이버네이트의 sql exception 을 Spring 의 DataAccessException 으로 변환해준다.
    @Test
    public void crud(){
        //@GeneratedValue 어노테이션을 제거하면, 이 코드는 id 가 없어서 하이버네이트에서 에러가 남.
        Post post = new Post();
        post.setTitle("jpa");
        postRepository.save(post);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    //스프링 데이터 JPA 2. JpaRepository.save() 메소드
    @Test
    public void save(){

        //Transient 상태 : 새로 만들어진 객체. 하이버네이트와 db는 이 객체를 모른다. id 가 없다.
        //Persistent 상태 : Persistence context 가 관리하는 상태. -> 하이버네이트가 똑똑하게 적절하게 db와 싱크함.
        //Detached 상태 : 한 번이라도 db에 persist 가 되었던 객체. 이 객체는 id 가 있을 수도 있고 없을 수도 있다.

        //Transient 인지 Detached 인지 판단 -> id의 유무 (기본전략)
        //참고 : 객체의 id 생성을 @GeneratedValue 말고 직접 넣어주는 경우에는 detached 객체라 간주한다.
        //      그런데 merge 를 할 때, db 에 없기 때문에 insert 가 발생한다.

        //save 시 객체가 transient 상태면 -> EntityManager.persist() ->  insert 발생
        //save 시 객체가 detached 상태면 -> EntityManager.merge() -> insert or update 발생.

        //인서트 발생
        //post 객체는 @id 프로퍼티의 값이 null 이라, Transient 상태. persist() 발생.
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post); //persist

        //save 의 리턴되는 인스턴스는 영속화 되어있는 객체이다.
        //따라서 entityManager 에 영속화된 객체의 결과가 같다.
        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post);

        //업데이트 발생
        //postUpdate 객체는 @id 프로퍼티 값이 있으면 Detached 상태로 판단하고, 업데이트 수행.
        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        Post updatedPost = postRepository.save(postUpdate); //merge

        //merge 는 메소드에 넘긴 엔티티의 복사본을 만들어서 persistent 상태로 변경한다.
        //그리고 영속화된 인스턴스를 리턴한다.(updatedPost)
        assertThat(entityManager.contains(updatedPost)).isTrue();

        //따라서 메소드에 넘긴 파라미터는 엔티티 매니저에 영속화된 객체와는 다르다.
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(updatedPost == postUpdate).isFalse();

        //결론 : save 하고 리턴된 객체가 persistent 상태의 객체이다. 이 객체를 써라. 파라미터의 post 쓰지말고.

        //updatedPost 는 영속화된 객체이고 persistent 상태로 관리되고있다.
        //setTitle 로 인해 객체 변화가 되어, 명시적으로 업데이트를 안해도 알아서 hibernate 에 의해 update 가 된다.
        updatedPost.setTitle("whiteship");

        //업데이트 되는 시점은 이 때. 하이버네이트가 find 할 때 객체상태변화가 되어 필요하다고 생각되어 싱크를 한다.
        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    //스프링 데이터 JPA 3. JPA 쿼리 메소드
    @Test
    public void findByTitleStartsWith(){
        Post post = new Post();
        post.setTitle("Spring Data Jpa");
        Post savedPost = postRepository.save(post);

        List<Post> all = postRepository.findByTitleStartingWith("Spring");
        assertThat(all.size()).isEqualTo(1);
    }

    private Post savePost(){
        Post post = new Post();
        post.setTitle("Spring");
        return postRepository.save(post);
    }
    @Test
    public void findByTitle() {
        savePost();
        List<Post> all = postRepository.findByTitle("Spring");
        assertThat(all.size()).isEqualTo(1);

        //스프링 데이터 JPA 4. Sort
        //Sort 에 들어가는 문자열은 프로퍼티 or Alias 여야 한다.
        //title 은 Post 엔티티의 프로퍼티.
        List<Post> all2 = postRepository
                .findByTitleQueryAnnotaion("Spring", Sort.by("title"));
        assertThat(all2.size()).isEqualTo(1);

        //JpaSort.unsafe 를 사용해서 Sort 에 들어가는 문자열을 함수로 변경 가능하다.
        List<Post> all3 = postRepository
                .findByTitleQueryAnnotaion("Spring", JpaSort.unsafe("LENGTH(title)"));
        assertThat(all3.size()).isEqualTo(1);
    }

    //스프링 데이터 JPA 6. Update 쿼리.
    @Test
    public void updateTitle(){
        Post spring = savePost();

        String hibernate = "hibernate";
        int updateCount = postRepository.updateTitle(hibernate, spring.getId());
        assertThat(updateCount).isEqualTo(1);

        //업데이트 쓰면 단점. -> persistent 상태로 관리되면 객체가 캐싱되어있어서 에러 발생 할 수 있음.

        //Post spring 객체는 한 트랜잭션 내에서, Persistent 캐시가 유지가 된다.
        //Persistent 상태에 있는 spring 객체가 있기에 db 에 findById 를 하지 않고 캐싱된 spring 객체를 그대로 가져온다.
        //해결법 -> 업데이트 쿼리 후에, persistence context 를 클리어해준다.
        //         @Modifying(clearAutomatically = true)
        Optional<Post> byId = postRepository.findById(spring.getId());
        assertThat(byId.get().getTitle()).isEqualTo(hibernate);
    }

    //그럼 제대로 된 update 쿼리를 만들어 보자.
    @Test
    public void realUpdateTitle(){
        Post spring = savePost();
        spring.setTitle("hibernate");

        //find 하기 전에 db 에 sync 를 맞춘다. (하이버네이트가)
        Optional<Post> byId = postRepository.findById(spring.getId());
        assertThat(byId.get().getTitle()).isEqualTo("hibernate");
    }
}
