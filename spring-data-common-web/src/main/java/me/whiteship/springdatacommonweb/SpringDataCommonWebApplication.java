package me.whiteship.springdatacommonweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 스프링 데이터 JPA:1. JpaRepository

@SpringBootApplication
//@EnableJpaRepositories -> 스프링 부트를 사용하면 @Configuration 에 의해 자동 설정 됨. 없어도 됨
//                        -> 이 어노테이션을 사용하면 JpaRepository 라는 인터페이스를 상속받은 PostRepository 의 프록시 빈을 등록해 준다.
@EnableJpaRepositories
public class SpringDataCommonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataCommonWebApplication.class, args);
    }

}
