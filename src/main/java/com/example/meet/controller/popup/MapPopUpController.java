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
        LocalDateTime markStartTime = markService.checkDay(startTime);
        LocalDateTime markEndTime = markService.checkDay(endTime);
        markEndTime = markService.checkEndTime(markStartTime, markEndTime);

        Mark mark = new Mark(markDto.getUsername(), markDto.getGender(), markDto.getAgeRange(),
                markDto.getCharacter(), markDto.getPlace(), markDto.getLat(), markDto.getLng(),
                markStartTime, markEndTime, markDto.getContents(), markDto.getParticipant());
        markRepository.save(mark);

        // ajax에서 markDto.startDay markDto.endDay를 계산해서 넘겨주지 않으므로
        // 파라미터로 받은 markDto를 쓰지 않고
        // 계산되어 저장되어 있는 DB에서 데이터를 꺼내어 리턴
        MarkDto result = markService.getMarkDto(mark);
        return result;
    }

}
