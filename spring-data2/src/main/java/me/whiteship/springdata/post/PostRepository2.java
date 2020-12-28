package me.whiteship.springdata.post;

import me.whiteship.springdata.MyRepository;

//8. 기본 리포지토리 커스터마이징
public interface PostRepository2 extends MyRepository<Post,Long> {
}
