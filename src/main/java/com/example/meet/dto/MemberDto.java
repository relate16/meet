package com.example.meet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class MemberDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Integer age;
    private String gender;

    private Integer cash;
}
