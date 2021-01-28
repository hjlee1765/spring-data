package me.whiteship.springdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Study {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // N:M 매핑

    //양방향 관계에서는 보통 관계의 주인이 foreign key 를 가지고 있는 쪽이다.
    @ManyToOne
    private Account owner;

/*
    //N : 1 매핑. (N이 주인. foreign key)
    //1명의 account 는 여러개의 study 를 만들 수 있다.

    //Study 안에 Account 를 가지고 있는 경우 = Study 가 주인 ->  foreign key 로 관리된다.
    //@ManyToOne 의 끝 부분이 "One" 이면 프로퍼티도 리스트가 아니라 단건.

    //study 는 테이블에 컬럼으로 "프로퍼티 명(owner) + _id" 를 붙여서 foreign key 를 생성한다. (owner 의 키)
    @ManyToOne
    private Account owner;
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
