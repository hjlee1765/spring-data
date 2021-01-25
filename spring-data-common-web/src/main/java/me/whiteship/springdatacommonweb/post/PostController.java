package me.whiteship.springdatacommonweb.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// spring data 가 제공하는 기능.
//스프링 데이터 Common 12. 웹 기능 2부 DomainClassConverter
//스프링 데이터 Common 13. 웹 기능 3부 Pageable 과 Sort

@RestController
public class PostController {

    //@autowired 대신, 생성자로 주입 가능.
    //단, 생성자가 1개여야하고 생성자가 받는 파라미터가 빈으로 등록되어 있는경우 자동으로 주입 가능.
    private final PostRepository postRepository;
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //변수이름과 path의 이름이 같으면 @PathVariable("id") 의 괄호 생략가능
    //path 에 들어온 id 의 값은 문자열. 이 문자열은 converter 에 의해 Long 으로 변환됨.
    @GetMapping("/posts/Origin/{id}")
    public String getPost(@PathVariable Long id){
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.get();
        return post.getTitle();
    }

    // Spring data 가 제공하는 기능 1.
    // Domain Class Converter
    // Converter 는 어떤 하나의 타입을 다른 타입으로 변경시키는 인터페이스이다.
    // Domain Class Converter 에 보면, ToEntityConverter 메서드가 있는데 id를 받아서 엔티티로 변경시켜준다.
    // 이 때 repository 를 사용해서 findById를 한다. 즉 변환시킬 때 post에 들어가는 값은 조회된 결과이다.
    // 그래서 위의 코드와(25,26줄)과 동일한 코드이다.
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") Post post){
        return post.getTitle();
    }

    //참고. Formatter 란?
    // converter 와 비슷하다. 차이점은 문자열기반 이라는 점.
    // 1. 문자열을 어떤 타입으로 변환할 것인가?
    // 2. 어떤 타입을 받았을 때, 어떠한 문자열로 변환할 것인가?


    //스프링 데이터 Common 13. 웹기능 3부 Pageable 과 Sort
    //spring data 가 제공하는 기능.2 - pageable / sort
    @GetMapping("/posts")
    public Page<Post> getPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }

}
