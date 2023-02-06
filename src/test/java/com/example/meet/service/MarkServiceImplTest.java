package com.example.meet.service;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import com.example.meet.repository.MarkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MarkServiceImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    MarkRepository markRepository;
    @Autowired
    MarkService markService;

    @Test
    void deleteMarksAfterNow() {
        /*    given    */
        // 하루 전
        Mark aDayAgo = new Mark("userDay", "male", "18~24", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now().plusDays(-2), LocalDateTime.now().plusDays(-1),
                "testDay", null);
        // 한 시간 전
        Mark aHourAgo = new Mark("userHour", "male", "25~29", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now().plusHours(-2), LocalDateTime.now().plusHours(-1),
                "testHour", null);
        // 1분 전
        Mark aMinuteAgo = new Mark("userMinute", "male", "25~29", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now().plusMinutes(-2), LocalDateTime.now().plusMinutes(-1),
                "testMinute", null);
        // 1초 전
        Mark aSecondAgo = new Mark("userSecond", "male", "25~29", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now().plusMinutes(-2), LocalDateTime.now().plusMinutes(-1),
                "testSecond", null);

        Mark markADayAgo = markRepository.save(aDayAgo);
        Mark markAHourAgo = markRepository.save(aHourAgo);
        Mark markAMinuteAgo = markRepository.save(aMinuteAgo);
        Mark markASecondAgo = markRepository.save(aSecondAgo);

        List<Long> markIds = new ArrayList<>();
        markIds.add(markADayAgo.getId());
        markIds.add(markAHourAgo.getId());
        markIds.add(markAMinuteAgo.getId());
        markIds.add(markASecondAgo.getId());

        /*    when    */
        markService.deleteMarksAfterNow();

        /*    then    */
        Assertions.assertThat(markRepository.findAllById(markIds).size()).isEqualTo(0);


    }

    @Test
    @Transactional
    void addParticipant() throws IOException {
        /*    given    */
        Mark mark = new Mark("userDay", "male", "18~24", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "testDay", null);
        Mark savedMark = markRepository.save(mark);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        /*    when    */
        Mark addMark = markService.addParticipant(savedMark.getId(), mockHttpServletResponse);

        // 1차 캐시에 savedMark 가 저장되어 있으므로 DB에서 찾아오기 전에 flush 후 영속성 컨텍스트 초기화.
        em.flush();
        em.clear();

        /*    then    */
        Optional<Mark> findMarkOpt = markRepository.findById(addMark.getId());
        Mark findMark = findMarkOpt.orElseThrow(() -> new RuntimeException("해당 id가 없습니다."));

        Assertions.assertThat(findMark.getParticipant()).isEqualTo(1);
    }

    @Test
    void checkEndTime() {
        /*    given    */
        // am
        String inputStartTime = "11:58 am";
        String inputEndTime = "11:55 am";

        //pm
        String inputStartTime2 = "12:00 pm";
        String inputEndTime2 = "11:00 pm";

        LocalDateTime markStartTime = markService.checkDay(inputStartTime);
        LocalDateTime markEndTime = markService.checkDay(inputEndTime);

        LocalDateTime markStartTime2 = markService.checkDay(inputStartTime2);
        LocalDateTime markEndTime2 = markService.checkDay(inputEndTime2);

        /*    when    */
        markEndTime = markService.checkEndTime(markStartTime, markEndTime);
        markEndTime2 = markService.checkEndTime(markStartTime2, markEndTime2);

        /*    then    */
        Assertions.assertThat(markEndTime.getDayOfMonth()).isEqualTo(markStartTime.plusDays(1).getDayOfMonth());
        Assertions.assertThat(markEndTime2.getDayOfMonth()).isEqualTo(markStartTime.plusDays(1).getDayOfMonth());

    }
    @Test
    void getMarkDto() {
        /*    given    */
        Mark mark = new Mark("userMarkDto", "male", "18~24", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "testMarkDto", null);

        /*    when    */
        MarkDto markDto = markService.getMarkDto(mark);

        /*    then    */
        Assertions.assertThat(markDto.getUsername()).isEqualTo(mark.getUsername());
        Assertions.assertThat(markDto.getGender()).isEqualTo(mark.getGender());
        Assertions.assertThat(markDto.getAgeRange()).isEqualTo(mark.getAgeRange());
        Assertions.assertThat(markDto.getCharacter()).isEqualTo(mark.getCharacter());
        Assertions.assertThat(markDto.getPlace()).isEqualTo(mark.getPlace());
        Assertions.assertThat(markDto.getLat()).isEqualTo(mark.getLat());
        Assertions.assertThat(markDto.getLng()).isEqualTo(mark.getLng());

        // DateTimeFormatter.ofPattern("yyyy년 MM월 dd일") :
        //      2023-01-21 00:00:01.702103 -> 2022년 01월 21일 으로 변환
        Assertions.assertThat(markDto.getStartYMD())
                .isEqualTo(mark.getStartTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
        Assertions.assertThat(markDto.getEndYMD())
                .isEqualTo(mark.getEndTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));

        Assertions.assertThat(markDto.getStartTime()).isEqualTo(convertMarkDtoTime(mark.getStartTime()));
        Assertions.assertThat(markDto.getEndTime()).isEqualTo(convertMarkDtoTime(mark.getEndTime()));
        Assertions.assertThat(markDto.getContents()).isEqualTo(mark.getContents());
        Assertions.assertThat(markDto.getParticipant()).isEqualTo(mark.getParticipant());

    }
    @Test
    void getMarkDtos() {
        /*    given    */
        List<Mark> marks = new ArrayList<>();
        Mark mark = new Mark("userMark", "male", "18~24", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "testMark", null);
        Mark mark2 = new Mark("userMark2", "male", "25~29", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "testMark2", null);
        Mark mark3 = new Mark("userMark3", "male", "30~34", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "testMark3", null);
        marks.add(mark);
        marks.add(mark2);
        marks.add(mark3);

        /*    when    */
        List<MarkDto> markDtos = markService.getMarkDtos(marks);

        /*    then    */
        for (int i = 0; i < markDtos.size(); i++) {
            Assertions.assertThat(markDtos.get(i)).isEqualTo(markService.getMarkDto(marks.get(i)));
        }
    }

    @Test
    void checkDay() {
        /*    given    */
        LocalDateTime localDTNow = LocalDateTime.now();

        // startTime 형식 예 : 10:58 AM
        String startTimeH = convertMarkDtoTime(localDTNow.plusHours(-1));
        String startTimeM = convertMarkDtoTime(localDTNow.plusMinutes(-1));

        /*    when    */
        LocalDateTime checkedStartTimeH = markService.checkDay(startTimeH);
        LocalDateTime checkedStartTimeM = markService.checkDay(startTimeM);

        /*    then    */
        Assertions.assertThat(checkedStartTimeH.getDayOfMonth()).isEqualTo(localDTNow.plusDays(1).getDayOfMonth());
        Assertions.assertThat(checkedStartTimeM.getDayOfMonth()).isEqualTo(localDTNow.plusDays(1).getDayOfMonth());
    }



    // private 메서드
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
