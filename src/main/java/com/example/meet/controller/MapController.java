package com.example.meet.controller;

import com.example.meet.dto.MarkDto;
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

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final MarkRepository markRepository;
    private final MarkService markService;

    @GetMapping("/")
    public String showMap(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            Integer cash = 0;
            String href1 = "/login";
            String href2 = "/signup";
            String login = "로그인";
            String signup = "회원가입";

            model.addAttribute("href1", href1); // top.html parameter
            model.addAttribute("href2", href2); // top.html parameter
            model.addAttribute("menu1", login); // top.html parameter
            model.addAttribute("menu2", signup); // top.html parameter
            model.addAttribute("menu3", cash); // top.html parameter
            model.addAttribute("localDateTime", LocalDateTime.now());
            return "meet-map";
        }

        Integer cash = loginMember.getCash();
        String href1 = "/logout";
        String href2 = "#!";
        String logout = "로그아웃";
        String username = loginMember.getUsername() + "님";

        model.addAttribute("href1", href1); // top.html parameter
        model.addAttribute("href2", href2); // top.html parameter
        model.addAttribute("menu1", logout); // top.html parameter
        model.addAttribute("menu2", username); // top.html parameter
        model.addAttribute("menu3", cash); // top.html parameter
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "meet-map-login";
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
