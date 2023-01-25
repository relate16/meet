package com.example.meet.service;

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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

        markService.deleteMarksAfterNow();

        Assertions.assertThat(markRepository.findAllById(markIds).size()).isEqualTo(0);


    }

    @Test
    @Transactional
    void addParticipant() throws IOException {
        Mark mark = new Mark("userDay", "male", "18~24", "조용함", "xx빌딩 2층 별다방",
                "37.566535", "126.977969", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "testDay", null);
        Mark savedMark = markRepository.save(mark);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Mark addMark = markService.addParticipant(savedMark.getId(), mockHttpServletResponse);

        // 1차 캐시에 savedMark 가 저장되어 있으므로 DB에서 찾아오기 전에 flush 후 영속성 컨텍스트 초기화.
        em.flush();
        em.clear();

        Optional<Mark> findMarkOpt = markRepository.findById(addMark.getId());
        Mark findMark = findMarkOpt.orElseThrow(() -> new RuntimeException("해당 id가 없습니다."));

        Assertions.assertThat(findMark.getParticipant()).isEqualTo(1);
    }

    @Test
    void checkEndTime() {

    }
    @Test
    void getMarkDto() {

    }
    @Test
    void getMarkDtos() {

    }

    @Test
    void checkDay() {

    }



}
