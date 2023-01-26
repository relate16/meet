package com.example.meet.service;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import com.example.meet.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;


    /**
     * 유효기간 지난 Mark 전부 삭제
     * 즉, 현재시간보다 endtime이 적은 mark 전부 삭제
     */
    @Override
    @Transactional
    public void deleteMarksAfterNow() {
        List<Mark> marksByEndTimeBefore = markRepository.findMarksByEndTimeBefore(LocalDateTime.now());
        for (Mark mark : marksByEndTimeBefore) {
            markRepository.delete(mark);
        }
    }

    /**
     * maker content창 참석 클릭시
     * 올 수도 있는 사람 명 수 추가
     */
    @Override
    @Transactional
    public Mark addParticipant(Long markId, HttpServletResponse response) throws IOException {
        Optional<Mark> markOpt = markRepository.findById(markId);
        String message = "해당 팝업창이 존재하지 않습니다.(id 미존재)";
        Mark mark = null;
        try {
            mark = markOpt.orElseThrow(() -> new RuntimeException(message));
            mark.addParticipant();
        } catch (RuntimeException e) {
            // 해당 id가 없으면 알림창 및 이전 페이지로 이동
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('" + message + "');history.go(-1);</script>");
            w.flush();
            w.close();
        }
        return mark;
    }


    /**
     * markService.checkDay(endTime)메서드 반환 값을
     * mark.endTime에 넣기 전에
     * endTime이 startTime보다 작을 경우 날짜 하루 더하는 메서드.
     *
     * 예를 들어
     * 고객이 2023년 01월 18일에 mdTimePicker 를 입력할 때,
     * startTime이 11:00 am 이고 endTime이 10:00 am 일 경우
     * markEndTime을 다음날인 2023년 01월 19일 10:00am 으로 설정하게함으로써
     * markStartTime, 2023년 01월 18일 11:00 am
     * marEndTime, 2023년 01월 19일 10:00 am이 되게 함.
     * @return Mark.endTime
     */
    @Override
    public LocalDateTime checkEndTime(LocalDateTime markStartTime, LocalDateTime markEndTime) {
        if (markEndTime.isBefore(markStartTime)) {
            markEndTime = markEndTime.plusDays(1);
        }
        return markEndTime;
    }



    //MarkDto 관련

    /**
     * Mark mark -> MarkDto markDto
     */
    @Override
    public MarkDto getMarkDto(Mark mark) {
        String startTime = convertMarkDtoTime(mark.getStartTime());
        String endTime = convertMarkDtoTime(mark.getEndTime());

        String startYMD = getStartDay(mark);
        String endYMD = getEndDay(mark);

        MarkDto markDto = new MarkDto(
                mark.getId(), mark.getUsername(), mark.getGender(),
                mark.getAge(), mark.getCharacter(), mark.getPlace(), mark.getLat(), mark.getLng(),
                startYMD, endYMD,
                startTime, endTime, mark.getContents(), mark.getParticipant());
        return markDto;
    }

    /**
     * List<Mark> marks -> List<MarkDto> markDots
     */
    @Override
    public List<MarkDto> getMarkDtos(List<Mark> marks) {
        ArrayList<MarkDto> markDtos = new ArrayList<>();

        for (Mark mark : marks) {
            String startTime = convertMarkDtoTime(mark.getStartTime());
            String endTime = convertMarkDtoTime(mark.getEndTime());

            String startYMD = getStartDay(mark);
            String endYMD = getEndDay(mark);

            MarkDto markDto = new MarkDto(
                    mark.getId(), mark.getUsername(), mark.getGender(), mark.getAge(),
                    mark.getCharacter(), mark.getPlace(), mark.getLat(), mark.getLng(),
                    startYMD, endYMD, startTime, endTime, mark.getContents(), mark.getParticipant()
            );

            markDtos.add(markDto);
        }
        return markDtos;
    }

    /**
     * 설정한 시간(startTime or endTime)이 다음날일 경우 날짜 하루 더하는 메서드.
     *
     * 파라미터를 startTime을 넣을 때로 예를 들면
     * 현재 시간이 2023년 01월 18일 11:00 am인데
     * 고객이 startTime을 10:00 am 정한 경우,
     * 2023년 01월 19일 10:00 am 처럼 변환
     * @param markDtoTime = MarkDto.startTime or MarkDto.endTime
     * @variable markTime = Mark.startTime or Mark.endTime
     */
    @Override
    public LocalDateTime checkDay(String markDtoTime) {
        LocalDateTime markTime;
        Integer hour;
        Integer minute;
        String regex = " ";
        String[] split = markDtoTime.split(regex);
        // split = ["2:00", "AM"]

        String time = split[0];
        String amPm = split[1];
        String[] splitTime = time.split(":");
        //splitTime = ["2", "00"]

        //시간을 0~12 AM or PM에서 00~24로 바꿔주는 로직
        if (split[1].equals("PM") && !splitTime[0].equals("12")) {
            // pm이면 12시간을 더함. 단, 12:00 pm일 경우 12시간 더하지 않음. 더하면 24:00이 됨
            hour = Integer.valueOf(splitTime[0]) + 12;
            minute = Integer.valueOf(splitTime[1]);
        } else if (split[1].equals("AM") && splitTime[0].equals("12")) {
            // 12:00 am일 경우 시간을 00시로 변경. 변경 안하면 12:00이 됨
            hour = Integer.valueOf(splitTime[0]) - 12;
            minute = Integer.valueOf(splitTime[1]);
        } else {
            // 나머지, 즉 am이면 시간, 분 그대로 씀.
            hour = Integer.valueOf(splitTime[0]) ;
            minute = Integer.valueOf(splitTime[1]);
        }

        // 설정한 머무는 시간이 다음날일 경우 날짜 하루 더하는 설정
        if ((hour - LocalDateTime.now().getHour()) * 60 + (minute - LocalDateTime.now().getMinute()) < 0) {
            markTime = LocalDateTime.now().plusDays(1).withHour(hour).withMinute(minute);
        } else {
            markTime = LocalDateTime.now().withHour(hour).withMinute(minute);
        }
        return markTime;
    }



    //private 메서드

    /**
     * mark.startTime 2023-01-21 00:00:01.702103 -> 2022년 01월 21일 으로 변환
     * '2022년 01월 21일' 형식은 MarkDto.startDay 형식이다.
     */
    private String getStartDay(Mark mark) {
        return mark.getStartTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }
    /**
     * mark.endTime 2023-01-21 00:00:01.702103 -> 2022년 01월 21일 으로 변환
     * '2022년 01월 21일' 형식은 MarkDto.endDay 형식이다.
     */
    private String getEndDay(Mark mark) {
        return mark.getEndTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }

    /**
     * LocalDateTime 형식인
     * Mark.startTime 이나 Mark.endTime 을
     * String 형식인
     * MarkDto.startTime이나 MarkDto.endTime으로 변환
     *
     * @param markTime = Mark.startTime or Mark.endTime
     * @return markDtoTime = MarkDto.startTime or MarkDto.endTime
     */
    private String convertMarkDtoTime(LocalDateTime markTime) {
        int hour = markTime.getHour();
        int minute = markTime.getMinute();
        String amPm = "AM";

        if (hour == 0) {
            // LocalDateTime에서 시간이 0시 일 경우 mdTimePicker 시간 형식인 "12:00 AM" 으로 바꿔주기 위함
            hour = 12;
        } else if(hour >= 12) {
            // LocalDateTime에서 12시 이후일 경우 mdTimePicker 시간 형식인 "12:00 PM" 처럼 바꿔주기 위함
            hour -= 12;
            amPm = "PM";
        }

        String stringMinute = String.valueOf(minute);
        if (stringMinute.length() == 1) {
            // LocalDateTime에서 2분일 경우 mdTimePicker 분 형식인 "12:02 PM"처럼 바꿔주기 위함
            stringMinute = "0" + stringMinute;
        }

        String markDtoTime = hour + ":" + stringMinute + " " + amPm;
        return markDtoTime;
    }
}
