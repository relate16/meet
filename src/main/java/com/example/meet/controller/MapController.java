package com.example.meet.controller;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import com.example.meet.repository.MarkRepository;
import com.example.meet.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final MarkRepository markRepository;
    private final MarkService markService;

    @GetMapping("/")
    public String showMap(Model model) {
        String username = "testName";
        Integer cash = 1000;
        String login = "로그인";
        String signup = "회원가입";

//                'testName','1000','로그인','회원가입'
        model.addAttribute("username", username);
        model.addAttribute("cash", cash);
        model.addAttribute("login", login);
        model.addAttribute("signup", signup);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "meet-map";
    }

    @ResponseBody
    @PostMapping("/get-marks")
    public List<MarkDto> getMarks() {
        markService.deleteMarksAfterNow();
        List<Mark> marks = markRepository.findAll();
        List<MarkDto> markDtos = markService.getMarkDtos(marks);
        return markDtos;
    }

    @ResponseBody
    @PostMapping("/update-mark")
    public MarkDto updateMark(@RequestBody Long markId, HttpServletResponse response) throws IOException {
        Mark mark = markService.addParticipant(markId, response);
        MarkDto markDto = markService.getMarkDto(mark);
        return markDto;
    }
}
