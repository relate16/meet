package com.example.meet.controller;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import com.example.meet.entity.Member;
import com.example.meet.repository.MarkQueryRepository;
import com.example.meet.repository.MarkRepository;
import com.example.meet.service.MarkService;
import com.example.meet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final MarkRepository markRepository;
    private final MarkService markService;
    private final MemberService memberService;
    private final MarkQueryRepository markQueryRepository;

    @GetMapping("/")
    public String showMap(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            Integer cash = 0;
            String href1 = "/login";
            String href2 = "/signup";
            String login = "로그인";
            String signup = "회원가입";

            setTopMenu(model, href1, href2, login, signup, cash); // 탑 레이아웃 메뉴 설정
            model.addAttribute("localDateTime", LocalDateTime.now());
            return "meet-map";
        }

        //session에 저장된 loginMember는 로그인할 때 고정 정보이므로
        //충전 후 변경된 사항이 페이지에 반영하기 위해서는 DB에서 member를 찾아와야 함.
        Member member = memberService.getMember(loginMember.getId());
        Integer cash = member.getCash();
        String href1 = "/logout";
        String href2 = "#!";
        String logout = "로그아웃";
        String username = loginMember.getUsername() + "님";

        setTopMenu(model, href1, href2, logout, username, cash); // 탑 레이아웃 메뉴 설정
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "meet-map-login";
    }

    @ResponseBody
    @PostMapping("/get-marks")
    public List<MarkDto> getMarks(@RequestBody(required = false) Integer untilTime) {
        markService.deleteMarksAfterNow();

        // 모든 marks 조회일 경우 - 예) localhost:8080/ 요청인 경우
        if (untilTime == null) {
            List<Mark> marks = markRepository.findAll();
            List<MarkDto> markDtos = markService.getMarkDtos(marks);
            return markDtos;
        }

        // marks 조건 검색일 경우 - 예) 현재 시각 2시간 이내
        List<Mark> marks = markQueryRepository.searchMarksByTime(untilTime);
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

    @PostMapping("/delete-mark")
    @ResponseBody
    public boolean deleteMark(@RequestBody Long markId, @SessionAttribute(name = LOGIN_MEMBER) Member loginMember) throws Exception {
        Optional<Mark> markOpt = markRepository.findById(markId);
        Mark mark = markOpt.orElseThrow(() -> new RuntimeException());
        if (mark.getMember().getId() != loginMember.getId()) {
            throw new Exception("해당 회원이 mark를 삭제할 권한이 없습니다.");
        }
        markRepository.delete(mark);
        return true;
    }


    // private 메서드

    private void setTopMenu(Model model, String href1, String href2, String menu1, String menu2, Integer menu3) {
        model.addAttribute("href1", href1); // top.html parameter
        model.addAttribute("href2", href2); // top.html parameter
        model.addAttribute("menu1", menu1); // top.html parameter
        model.addAttribute("menu2", menu2); // top.html parameter
        model.addAttribute("menu3", menu3); // top.html parameter
    }

}
