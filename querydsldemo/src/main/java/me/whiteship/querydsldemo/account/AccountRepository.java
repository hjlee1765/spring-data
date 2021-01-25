package me.whiteship.querydsldemo.account;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//query dsl 사용을 위해 인터페이스의 다중 상속
public interface AccountRepository extends JpaRepository<Account, Long>
        , QuerydslPredicateExecutor<Account> {
}
