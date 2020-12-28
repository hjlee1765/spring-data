package me.whiteship.springdata.post;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Date;

//AbstractAggregateRoot : 스프링 데이터 도메인 이벤트에 필요한 기능이 구현되어있다.
@Entity
public class Post extends AbstractAggregateRoot<Post> {

    @Id @GeneratedValue
    private  Long id;

    private String title;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    //Post 객체에 대한 event 를 만들어서 registerEvent 에 넘겨주면,
    //DomainEvents 에 있는 이벤트를 퍼블리싱 시킨다.
    //AfterDomainEventPublication
    public Post posting() {
        this.registerEvent(new PostPublishedEvent(this));
        return this;
    }
}
