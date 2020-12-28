package me.whiteship.springdata;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Cascade 를 위해 parent-child 구조가 성립하는 예시를 만든다. 게시물이 사라지면 댓글 다 없어져야 하니까.

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    //전파.
    //PERSIST 상태만 전파됨. 보통 ALL 로 두면 테이블의 삭제,생성 모두 다 전파.

    //Fetch.
    //기본적으로 OneToMany 는 Lazy 이다. post 를 가져올 때, comments 는 가져오기 않는다.
    // Eager : comment 테이블을 left 조인해서 같이 가져온다.
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment){
        this.getComments().add(comment);
        comment.setPost(this);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
