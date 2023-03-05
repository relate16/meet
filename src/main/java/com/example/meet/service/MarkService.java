package com.example.meet.service;

import com.example.meet.entity.Mark;
import org.springframework.stereotype.Service;
import com.example.meet.dto.MarkDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MarkService {
    //mark 관련
    /**
     * 유효기간 지난 Mark 삭제
     * 즉, 현재시간보다 endtime이 적은 mark 삭제
     */
    void deleteMarksAfterNow();

    /**
     * maker content창 참석 클릭시
     * 올 수도 있는 사람 명 수 추가
     */
    Mark addParticipant(Long markId, HttpServletResponse response) throws IOException;

    /**
     * mark.endTime에 endTime 값을 넣기 전에
     * endTime이 startTime보다 작을 경우 날짜 하루 더하는 메서드.
     * <p>
     * 예를 들어
     * 고객이 2023년 01월 18일에 mdTimePicker 를 입력할 때,
     * startTime이 11:00 am 이고 endTime이 10:00 am 일 경우
     * markEndTime을 다음날인 2023년 01월 19일 10:00am 으로 설정하게함으로써
     * markStartTime, 2023년 01월 18일 11:00 am
     * marEndTime, 2023년 01월 19일 10:00 am이 되게 함.
     * @return mark.endTime
     */
    LocalDateTime checkEndTime(LocalDateTime markStartTime, LocalDateTime markEndTime);


    //markDto 관련
    /**
     * Mark mark -> MarkDto markDto
     */
    MarkDto getMarkDto(Mark mark);

    /**
     * List<Mark> marks -> List<MarkDto> markDots
     */
    List<MarkDto> getMarkDtos(List<Mark> marks);

    /**
     * 설정한 시간(startTime or endTime)이 다음날일 경우 날짜 하루 더하는 메서드.
     * <p>
     * 파라미터를 startTime을 넣을 때로 예를 들면
     * 현재 시간이 2023년 01월 18일 11:00 am인데
     * 고객이 startTime을 10:00 am 정한 경우,
     * 2023년 01월 19일 10:00 am 처럼 변환
     *
     * @param markDtoTime = MarkDto.startTime or MarkDto.endTime
     * @variable MarkTime = Mark.startTime or Mark.endTime
     */
    LocalDateTime checkDay(String markDtoTime);

}
