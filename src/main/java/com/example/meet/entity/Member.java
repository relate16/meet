package com.example.meet.entity;

import com.example.meet.upload.domain.UploadFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Embedded
    private UploadFile profileImgFile;


    public Member(String username, String password, Integer age, String gender, Integer cash) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.cash = cash;
    }

    //비지니스 로직
    public void chargeCash(int chargeAmount) {
        this.cash += chargeAmount;
    }

    public void updateProfileImgFile(UploadFile uploadFile) {
        this.profileImgFile = uploadFile;
    }
}
