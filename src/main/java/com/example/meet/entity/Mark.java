package com.example.meet.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Mark {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String gender;
    private String ageRange; //18~24, 25~29, 30~34, 35~39, 40~44, 45~49, 50~54, 55~59
    private String character;

    private String place;

    //위도
    private String lat;

    //경도
    private String lng;

    private LocalDateTime startTime; // 2023-01-21 00:00:01.702103
    private LocalDateTime endTime; // 2023-01-21 00:00:01.702103

    private String contents;

    private Integer participant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    // 생성자
    public Mark(String username, String gender, String ageRange, String character, String place, String lat, String lng,
                LocalDateTime startTime, LocalDateTime endTime, String contents, Integer participant) {
        this.username = username;
        this.gender = gender;
        this.ageRange = ageRange;
        this.character = character;
        this.place = place;
        this.lat = lat;
        this.lng = lng;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contents = contents;
        this.participant = participant;
    }

    public Mark(String username, String gender, String ageRange, String character, String place, String lat, String lng,
                LocalDateTime startTime, LocalDateTime endTime, String contents, Integer participant, Member member) {
        this.username = username;
        this.gender = gender;
        this.ageRange = ageRange;
        this.character = character;
        this.place = place;
        this.lat = lat;
        this.lng = lng;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contents = contents;
        this.participant = participant;
        this.member = member;
    }


    //비지니스로직
    public void addParticipant() {
        if (participant == null) {
            participant = 1;
        } else {
            participant += 1;
        }
    }
}
