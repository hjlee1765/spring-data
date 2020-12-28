package me.whiteship.springdata.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostRepositoryTestConfig {

    //리스너를 빈으로 등록한다.
    @Bean
    public PostListener postListener23(){
        return new PostListener();
    }
}
