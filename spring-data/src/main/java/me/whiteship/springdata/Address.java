package me.whiteship.springdata;

import javax.persistence.Embeddable;

//Account class: 엔티티 타입
//Address class: value 타입.( 엔티티에 종속적임)

//Embeddable : value 타입을 만들때 사용.
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;
    private String zipCode;
}
