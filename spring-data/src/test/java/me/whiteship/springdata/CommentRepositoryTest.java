package me.whiteship.springdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//스프링 데이터 JPA 활용 > 스프링 데이터 Common 4. 쿼리만들기.
//스프링 데이터 JPA 활용 > 스프링 데이터 Common 4. 쿼리만들기 실습.
//스프링 데이터 JPA 활용 > 스프링 데이터 Common 6. 비동기 쿼리 메소드.

//h2 를 사용함.
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud() throws ExecutionException, InterruptedException {
        this.createComment(100, "spring data jpa");
        this.createComment(55, "hibernate SPRING");
        //commentRepository.flush();

        // Page 예제
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "likeCount"));
        Page<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);
        assertThat(comments.getTotalElements()).isEqualTo(2);
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 100);

        // Async 예제
        // 1. Future
/*
        Future<List<Comment>> future =
                commentRepository.findByCommentContains("spring", pageRequest);

        System.out.println("===============");
        System.out.println("is done?" + future.isDone());

        List<Comment> res = future.get();
        res.forEach(System.out::println);*/

        // 2. ListenableFuture - callback 추가 가능함.
        ListenableFuture<List<Comment>> Lfuture =
                commentRepository.findByCommentContains("spring", pageRequest);

        System.out.println("===============");
        System.out.println("is done?" + Lfuture.isDone());

        Lfuture.addCallback(new ListenableFutureCallback<List<Comment>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(List<Comment> comments) {
                System.out.println("===============");
                //데이터가 안나온다.
                //왜냐면 메인스레드 A에서 insert 쿼리가 날라갔지만,
                //sub 스레드 B는 A에서 했던 짓을 모른다. 이미 데이터가 저장된 애들은 B에서 읽을 수 있으나
                //A 스레드가 저장한건 모른다.
                comments.forEach(System.out::println);
                System.out.println("====결과 안나옴====");

                //그래서 이런이유로 async select 방법은 비추...
                //결국 async 로 호출하나 안하나,
                //db가 가지고있는 db connection pool 로 가고, sql 실행하는 것이다. 부하는 같다.
                //단지 메인스레드의 자원을 효율적으로 쓸 수 있긴 하지만 <- 성능 그닥 안좋아진다..
                //가능하다면 spring webflux 를 써라. 단 아직 reactive 를 지원하는 db가 nosql밖에 없다?
            }
       });

        Thread.sleep(300);
    }

    private void createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setLikeCount(likeCount);
        commentRepository.save(newComment);
    }

}