package me.whiteship.springdatacommonweb.post;

import javax.persistence.*;
import java.util.Date;

@Entity
//엔티티 위에 미리 jpql, native 쿼리를 정의해 놓고 쿼리 사용가능하다.
//name 은 "엔티티.메서드이름" 으로 정의한다.
@NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post AS p WHERE p.title = ?1")
public class Post {
    @Id @GeneratedValue
    private Long id;

    private String title;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
