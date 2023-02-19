package com.example.meet.controller;

import com.example.meet.dto.MemberSignupDto;
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

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final MemberService memberService;
    private final AlertService alertService;

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute MemberSignupDto memberSignupDto, Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@Validated @ModelAttribute MemberSignupDto memberSignupDto, BindingResult bindingResult, Model model) {

        // 글로벌 error
        if (!memberSignupDto.getGender().equals("남") && !memberSignupDto.getGender().equals("여")
                && !memberSignupDto.getGender().equals("비공개")) {
            bindingResult.reject("validationGender");
        }
        if (!memberSignupDto.getPassword().equals(memberSignupDto.getPasswordRe())) {
            bindingResult.reject("validationPasswordRe");
        }
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // 회원가입
        memberService.saveMember(memberSignupDto);

        return "redirect:/signup/redirect";
    }

    /**
     * 알림창을 띄우기 위한 메서드
     */
    @GetMapping("/signup/redirect")
    public void afterPostSignup(HttpServletResponse response, Model model) {
        String message = "회원가입이 완료되었습니다.";
        String href = "/login";
        alertService.notificationWindow(message, href, response);
    }


}
