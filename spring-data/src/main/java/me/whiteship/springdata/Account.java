package me.whiteship.springdata;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Entity : 클래스가 db의 account 테이블과 매핑시켜주는 어노테이션.
//          name 설정 안하면 클래스명 = 테이블명.
//@Table :  생략되어있음. Table의 name은 Entity의 name.
//이 어노테이션때문에 Account클래스의 프로퍼티는 db의 컬럼으로 자동 매핑이된다.
@Entity(name = "Account")
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    //N:M 매핑
    //양방향 관계에서는 oneToMany 에서 mappedBy로 반대쪽 관계를 설정한다.
    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();

    //양방향 관계 매핑.
    public void addStudy(Study study) {
        this.getStudies().add(study);   //객체지향적으로 생각했을 때 넣어주는 코드임.
        study.setOwner(this);           //주인쪽에 관계를 꼭 설정해준다. (이 코드 없으면 foreign key 안들어감)
    }

    public void removeStudy(Study study){
        this.getStudies().remove(study);
        study.setOwner(null);
    }

/*

    //1 : N 매핑.(1이 주인, 조인테이블)
    //1명의 account 는 여러개의 study 를 만들 수 있다.

    //Account 안에 Study Set 을 가지고 있는 경우 -> 조인테이블이 생성되고 관계 정보가 저장됨. (account_studies)
    @OneToMany
    private Set<Study> studies = new HashSet<>();

*/

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    private String yes;

    @Transient
    private String no;

    //value 타입임을 알려줌(composite).
    //db에 insert 될 때, Address 클래스의 프로퍼티들이 flat 하게 Account 의 컬럼으로 들어감.
    @Embedded
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }
}
