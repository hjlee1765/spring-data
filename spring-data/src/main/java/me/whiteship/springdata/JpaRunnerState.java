package me.whiteship.springdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


// JPA 프로그래밍 5.엔티티 상태


@Component
@Transactional  //entityManager 와 관련 모든 오퍼레이션들은 한 트랜잭션안에서 일어나야 한다.
public class JpaRunnerState implements ApplicationRunner {


    //@Autowired 써도 되지만, JPA 어노테이션(PersistenceContext)을 써도 된다.
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception{
/*        //Transient 상태
        //hibernate 가 이 객체를 전혀 모른다, db에 매핑되어있는 상태가 전혀 아니다.
        Account account = new Account();
        account.setUsername("hjlee1765");
        account.setPassword("jpa");

        Study study = new Study();
        study.setName("Spring Data JPA");

        //양방향 관계를 설정하는 코드
        account.addStudy(study);

        Session session = entityManager.unwrap(Session.class);

        //Persistent 상태.
        //hibernate 가 객체를 관리하게 된다. 단 save 를 했다고 해서 db에 바로 들어가는 것은 아님.
        // 기능
        //        - 1차캐싱 : session 이라는 persistent context 에 넣는다.
        //                  persistent context 에서 관리중인 객체에 대해서 변경사항을 모니터링 하면서, 적절하게 db에 반영한다.
        //        - dirty checking : 객체의 변경사항을 모니터링한다.
        //        - write behind : 객체 상태변화를 최대한 늦게(적절한 시점에) db에 반영한다.
        session.save(account);
        session.save(study);

        //session 에서 캐시하고 있기 때문에, select 쿼리를 하지않는다.
        Account hjlee = session.load(Account.class, account.getId());

        //객체의 값을 바꿧을 뿐인데, hibernate 는 insert 후 update 를 해준다.
        //하지만 결국 객체가 변경되지 않는다면 update 자체가 일어나지 않는다. (write behind)
        hjlee.setUsername("changed my name");
        hjlee.setUsername("changed my name2");
        hjlee.setUsername("hjlee1765");

        System.out.println("===============================");
        System.out.println("session 에서 가져옴 : " + hjlee.getUsername());

        //Detached 상태.
        //JPA 가 더이상 관리하지 않는 상태.
        //트랜잭션이 종료되고, hjlee 라는 Account 객체가 외부에서 사용될때(=리턴해서 서비스에서 결과를 사용할 때)
        //이 시점에는 세션이 끝났기에, hibernate 가 관리하지 않는다.

        //re attache 하면 다시 persistent 상태로 변경 가능 하다.

        //Removed 상태
        //JPA 가 관리하긴 하지만 삭제하기로 한 상태.*/
    }
}
