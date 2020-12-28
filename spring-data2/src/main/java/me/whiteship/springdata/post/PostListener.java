package me.whiteship.springdata.post;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// 스프링 데이터 Common 9. 도메인 이벤트

//리스너 만들기
//참고 : ApplicationListener<PostPublishedEvent> 대신 @EventListener 를 메서드위에 달아도 된다!!
public class PostListener implements ApplicationListener<PostPublishedEvent> {

    @Override
    public void onApplicationEvent(PostPublishedEvent event) {
        System.out.println("----------------------");
        System.out.println(event.getPost() + "is published!!");
        System.out.println("----------------------");
    }
}
