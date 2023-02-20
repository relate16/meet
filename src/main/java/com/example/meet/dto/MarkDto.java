package com.example.meet.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarkDto {

    @NotNull
    private Long id; // mark.getId() 와 동일

    @NotNull
    @Column(length = 8)
    private String username;

    @NotNull
    private String gender;

    @NotNull
    private String ageRange;

    @NotNull
    @Column(length = 20)
    private String character;

    @NotNull
    private String place;

    //위도
    @NotNull
    private String lat;

    //경도
    @NotNull
    private String lng;


    private String startYMD; // yyyyMMdd
    private String endYMD; // yyyyMMdd

    @NotNull
    private String startTime; // 12:00 AM
    @NotNull
    private String endTime; // 12:00 AM

    @Column(length = 200)
    private String contents;

    private Integer participant;

}
