package com.example.meet.controller;

import com.example.meet.dto.MemberSingupDto;
import com.example.meet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute MemberSingupDto memberSingupDto, Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "signup";
    }
    @PostMapping("/signup")
    public String postSignup(@Validated @ModelAttribute MemberSingupDto memberSingupDto, BindingResult bindingResult, HttpServletResponse response , Model model) {

        // 글로벌 error
        if (!memberSingupDto.getGender().equals("남") && !memberSingupDto.getGender().equals("여")
                && !memberSingupDto.getGender().equals("비공개")) {
            bindingResult.reject("validatedGender");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // 회원가입
        memberService.saveMember(memberSingupDto);

        String message = "회원가입이 완료되었습니다.";
        notificationWindow(message, response);
        return "redirect:/";
    }

    /**
     * 알림창
     */
    private static void notificationWindow(String message,HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('" + message + "');location.href='/';</script>");
            w.flush();
            w.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
