package me.whiteship.springdatacommonweb.post;

import javax.persistence.*;

//스프링 데이터 JPA 7. EntityGraph
//@NamedEntityGraph 를 사용하면, 각각의 쿼리 메서드에서 각자 다른 fetch 전략을 설정 가능함.
@NamedEntityGraph(name = "Comment.post",
        attributeNodes = @NamedAttributeNode("post"))
@Entity
public class Comment{

    @Id @GeneratedValue
    private Long id;

    private String comment;

    //N:1 관계.
    //ManyToOne 은 fetchType.EAGER 가 디폴트라, Comment find 시, Post 까지 조인해서 같이 가져옴.
    //LAZY 로 바꾸면 Comment 만 가져오고, 나중에 Post 에 접근 시 그 때 가져온다.
    @ManyToOne(fetch = FetchType.LAZY)
    //@ManyToOne
    private Post post;

    private int up;

    private int down;

    private boolean best;

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public boolean isBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
