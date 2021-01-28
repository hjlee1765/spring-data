package me.whiteship.springdatacommonweb.post;

// 스프링 데이터 JPA 8. Projection

public interface CommentSummary {
    String getComment();

    int getUp();

    int getDown();

    default String getVotes(){
        return getUp() + " " + getDown();
    }
}
