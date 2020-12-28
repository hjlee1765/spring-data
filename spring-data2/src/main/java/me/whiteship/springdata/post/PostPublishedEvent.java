package me.whiteship.springdata.post;

import org.springframework.context.ApplicationEvent;

//9.도메인 이벤트

//이벤트 만들기
public class PostPublishedEvent extends ApplicationEvent {

    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }

    public Post getPost(){
        return post;
    }
}
