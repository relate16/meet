package com.example.meet.controller.popup;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import com.example.meet.repository.MarkRepository;
import com.example.meet.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MapPopUpController {

    private final MarkRepository markRepository;
    private final MarkService markService;

    @GetMapping("/regist-mark")
    public String registMark(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "popup/regist-mark";
    }

    @PostMapping("/insert-mark")
    @ResponseBody
    public MarkDto insertMark(@RequestBody MarkDto markDto) {

        String startTime = markDto.getStartTime();
        String endTime = markDto.getEndTime();

        //checkDay()와 checkEndTime() 를 같이 써야 각종 상황에 맞는 시간 설정 가능
        LocalDateTime markStartTime = checkDay(startTime);
        LocalDateTime markEndTime = checkDay(endTime);
        markEndTime = checkEndTime(markStartTime, markEndTime);

        Mark mark = new Mark(markDto.getUsername(), markDto.getGender(), markDto.getAge(),
                markDto.getCharacter(), markDto.getPlace(), markDto.getLat(), markDto.getLng(),
                markStartTime, markEndTime, markDto.getContents(), markDto.getParticipant());
        markRepository.save(mark);

        // ajax에서 markDto.startDay markDto.endDay를 계산해서 넘겨주지 않으므로
        // 파라미터로 받은 markDto를 쓰지 않고
        // 계산되어 저장되어 있는 DB에서 데이터를 꺼냄
        MarkDto result = markService.getMarkDto(mark);
        return result;
    }

    /**
     * endTime이 startTime보다 작을 경우 날짜 하루 더하는 메서드.
     *
     * 예를 들어
     * 고객이 2023년 01월 18일에 mdTimePicker 를 입력할 때,
     * startTime이 11:00 am 이고 endTime이 10:00 am 일 경우
     * markEndTime을 다음날인 2023년 01월 19일 10:00am 으로 설정하게함으로써
     * markStartTime, 2023년 01월 18일 11:00 am
     * marEndTime, 2023년 01월 19일 10:00 am이 되게 함.
     */
    private LocalDateTime checkEndTime(LocalDateTime markStartTime, LocalDateTime markEndTime) {
        if (markEndTime.isBefore(markStartTime)) {
            markEndTime = markEndTime.plusDays(1);
        }
        return markEndTime;
    }

    /**
     * 설정한 시간이 다음날일 경우 날짜 하루 더하는 메서드.
     *
     * 파라미터를 startTime을 넣을 때로 예를 들면
     * 현재 시간이 2023년 01월 18일 11:00 am인데
     * 고객이 startTime을 10:00 am 정한 경우,
     * 2023년 01월 19일 10:00 am 처럼 변환
     * @param markDtoTime = MarkDto.startTime or MarkDto.endTime
     * @variable markTime = Mark.startTime or Mark.endTime
     */
    private LocalDateTime checkDay(String markDtoTime) {
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
        if ((hour - LocalDateTime.now().getHour()) * 60 - (minute - LocalDateTime.now().getMinute()) < 0) {
            markTime = LocalDateTime.now().plusDays(1).withHour(hour).withMinute(minute);
        } else {
            markTime = LocalDateTime.now().withHour(hour).withMinute(minute);
        }
        return markTime;
    }


}
