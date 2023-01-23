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
