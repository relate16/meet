package com.example.meet.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Integer age;
    private String gender;

    private Integer cash;
}
