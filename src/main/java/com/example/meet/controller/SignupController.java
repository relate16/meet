package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import com.example.meet.entity.Member;
import com.example.meet.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final MemberRepository memberRepository;

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "signup";
    }
    @PostMapping("/signup")
    public void postSignup(@ModelAttribute MemberDto memberDto, HttpServletResponse response , Model model) {
        memberDto.setCash(0);
        Member member = new Member(memberDto.getUsername(), memberDto.getPassword(),
                memberDto.getAge(), memberDto.getGender(), memberDto.getCash());
        memberRepository.save(member);
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('회원가입이 완료되었습니다.');location.href='/';</script>");
            w.flush();
            w.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
