package com.example.meet.dto;

import lombok.Data;

@Data
public class MemberDto {

    private String username;

    private String password;

    private Integer age;
    private String gender;

    private Integer cash;
}
