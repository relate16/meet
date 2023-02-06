package com.example.meet.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Integer age;
    private String gender;

    private Integer cash;

}
