package me.whiteship.querydsldemo.account;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//query dsl 사용을 위해 인터페이스의 다중 상속
//QuerydslPredicateExecutor 를 상속받으면 타입 세이프한 쿼리를 만들 수 있게 도와준다.
//target.generated-sources.... 아래에 QAccount 라는 클래스가 생김.
public interface AccountRepository extends JpaRepository<Account, Long>
        , QuerydslPredicateExecutor<Account> {
}
