package me.whiteship.springdata;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

// 스프링 데이터 Common 8. 기본 리포지토리 커스터마이징.

public class SimpleMyRepositroy<T,ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T,ID> {

    private EntityManager entityManager;

    public SimpleMyRepositroy(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }
}
