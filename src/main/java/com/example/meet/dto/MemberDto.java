package com.example.meet.dto;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    @NotBlank
    private String passwordRe;

    @NotNull
    private Integer age;

    @NotBlank
    private String gender;

    private Integer cash;

    // 이거 글로벌 에러로 처리해야 할 듯 bindingResult.reject()
    @AssertTrue(message = "성별은 남, 여, 비공개 중 1개를 택해 입력해주세요")
    public boolean isGenderValidation() {
        if (gender.equals("남") || gender.equals("여") || gender.equals("비공개")) {
            return true;
        } else {
            return false;
        }
    }
}
