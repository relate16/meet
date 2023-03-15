package com.example.meet.dto;

import com.example.meet.upload.domain.UploadFile;
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

    private UploadFile profileImg;

    public MemberDto(Long id, String username, String password, Integer age, String gender, Integer cash) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.cash = cash;
    }
}
