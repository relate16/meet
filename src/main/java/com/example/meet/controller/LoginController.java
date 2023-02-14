package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import com.example.meet.entity.Member;
import com.example.meet.repository.MemberRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;

    // 세션 전에 로그인 기능 구현부터 하기
    @GetMapping("/login")

    public String getLogin(@ModelAttribute MemberDto memberDto, Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "login";
    }

    @PostMapping("/login")
    @Transactional
    public String postLogin(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult, HttpServletRequest request, Model model) {
        // 로그인 확인용 회원 가입 코드
        Member memberTest = new Member("test", "test", 10, "남", 0);
        memberRepository.save(memberTest);

        if (bindingResult.hasErrors()) {
            return "login";
        }

        Optional<Member> memberOpt = memberRepository.findByUsername(memberDto.getUsername());

        Member member = null;
        try {
            member = memberOpt.orElseThrow(() -> new RuntimeException("해당 username이 없습니다."));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        if (!memberDto.getPassword().equals(member.getPassword())) {
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
