package me.whiteship.springdata.post;

import java.util.List;

// 스프링 데이터 Common 7. 커스텀 리포지토리
// 목적 : "하나의 엔티티"의 스프링데이터 repo 인터페이스에 추가하고 싶은 기능이 있거나, 기능 덮어쓰고 싶을 때.
public interface PostCustomRepository<T> {

    List<Post> findMyPost();

    void delete(T entity);
}
