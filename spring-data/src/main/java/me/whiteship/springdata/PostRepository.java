package me.whiteship.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 인터페이스에서 상속을 받아서 CRUD 사용 가능하다. (참고 : 인터페이스는 다중상속 가능)
// JpaRepository<엔티티타입, pk 타입> 상속.
// 상속받을경우, @Repository 가 없어도 빈으로 등록해 줌.
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContains(String title, Pageable pageable);

}

/*
  // 아래와 같이 저장, 제거, 찾기와 같은 뻔한 메서드들을 항상 구현하는건 너무나 귀찮....

    @PersistenceContext
    EntityManager entityManager;

    public Post add(Post post){
        entityManager.persist(post);
        return post;
    }
    public void delete(Post post){
        entityManager.remove(post);
    }

    public List<Post> findAll(){
        return entityManager.createQuery("SELECT p FROM Post As p", Post.class)
                .getResultList();
    }*/
