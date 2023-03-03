package com.example.meet.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private Integer age;
    private String gender;

    private Integer cash;

    public Member(String username, String password, Integer age, String gender, Integer cash) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.cash = cash;
    }
}
