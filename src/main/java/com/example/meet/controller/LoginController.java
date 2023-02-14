package com.example.meet.controller;

import com.example.meet.dto.MemberLoginDto;
import com.example.meet.dto.MemberSingupDto;
import com.example.meet.entity.Member;
import com.example.meet.repository.MemberRepository;
import com.example.meet.service.AlertService;
import com.example.meet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AlertService alertService;

    @GetMapping("/login")
    public String getLogin(@ModelAttribute MemberLoginDto memberLoginDto, Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Validated @ModelAttribute MemberLoginDto memberLoginDto, BindingResult bindingResult,
                            HttpServletRequest request, HttpServletResponse response, Model model) {
        // 로그인 확인용 회원 가입 코드. (임시)
        MemberSingupDto memberDtoTest = new MemberSingupDto("test", "test", "test", 10, "남", null);
        memberService.saveMember(memberDtoTest);

        if (bindingResult.hasErrors()) {
            String message = "시스템 오류로 로그인에 실패했습니다. 관리자에게 문의해주세요.";
            alertService.notificationWindow(message, response);
            return "login";
        }

        Optional<Member> memberOpt = memberRepository.findByUsername(memberLoginDto.getUsername());

        Member member = null;
        try {
            member = memberOpt.orElseThrow(() -> new RuntimeException("해당 username이 없습니다."));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        if (!memberLoginDto.getPassword().equals(member.getPassword())) {
            // 패스워드 불일치
        }

        // 로그인 성공시 세션 만듦
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, "로그인된 회원");
        System.out.println("session.getId() = " + session.getId());
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "redirect:/";
    }
}
