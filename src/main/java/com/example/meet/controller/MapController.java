package com.example.meet.controller;

import com.example.meet.dto.MarkDto;
import com.example.meet.dto.MemberDto;
import com.example.meet.entity.Mark;
import com.example.meet.entity.Member;
import com.example.meet.repository.MarkRepository;
import com.example.meet.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showMap(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            Integer cash = 0;
            String login = "로그인";
            String signup = "회원가입";

            model.addAttribute("main1", login);
            model.addAttribute("main2", signup);
            model.addAttribute("main3", cash);
            model.addAttribute("localDateTime", LocalDateTime.now());
            return "meet-map";
        }

        Integer cash = loginMember.getCash();
        String logout = "로그아웃";
        String signout = "회원탈퇴";
        model.addAttribute("main1", logout);
        model.addAttribute("main2", signout);
        model.addAttribute("main3", cash);
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
