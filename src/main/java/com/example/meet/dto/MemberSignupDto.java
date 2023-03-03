package com.example.meet.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignupDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    @NotBlank
    private String passwordRe;

    @NotNull
    @Min(value = 0)
    @Max(value = 120)
    private Integer age;

    @NotBlank
    private String gender;

    private Integer cash;
}
