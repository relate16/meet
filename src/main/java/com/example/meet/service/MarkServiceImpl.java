package com.example.meet.service;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class MarkServiceImpl implements MarkService {

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
