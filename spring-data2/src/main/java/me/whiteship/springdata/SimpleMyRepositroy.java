package me.whiteship.springdata;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

// 스프링 데이터 Common 8. 기본 리포지토리 커스터마이징.
// SimpleMyRepositroy => MyRepository 의 구현체.

// 해당 구현체를 스프링데이터 jpa 는 아직 모른다. 그래서 메인클레스의 @EnableJpaRepositories로 설정을 해줘야 한다.
// SimpleJpaRepository = JpaRepository 의 구현체. 이거까지 상속받아야 jpa 안에 있던 기존 기능 사용 가능.
public class SimpleMyRepositroy<T,ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T,ID> {

    private EntityManager entityManager;

    //SimpleJpaRepository 를 상속받고 있어서, super 호출 할 때, 두 인자를 넘겨야함.
    //빈을 주입받아서 전달해줘야함.
    public SimpleMyRepositroy(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }
}
