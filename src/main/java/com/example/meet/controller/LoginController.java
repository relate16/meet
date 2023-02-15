package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.MemberSignupDto;
import com.example.meet.entity.Member;
import com.example.meet.repository.MemberRepository;
import com.example.meet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
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

    @GetMapping("/login")
    public String getLogin(@ModelAttribute MemberDto memberDto, Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "login";
    }

    @PostConstruct
    public void init() {
        // 로그인 확인용 회원 가입 코드. (임시)
        MemberSignupDto memberDtoTest = new MemberSignupDto("test", "test", "test", 10, "남", null);
        memberService.saveMember(memberDtoTest);
    }

    @PostMapping("/login")
    public String postLogin(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult,
                            HttpServletRequest request, Model model) {
        // 유효성 검사
        Optional<Member> memberOpt = memberRepository.findByUsername(memberDto.getUsername());
        if (!memberOpt.isPresent()) {
            bindingResult.reject("notFoundMember");
        }
        if (bindingResult.hasErrors()) {
            return "login";
        }

        Member member = memberOpt.orElseThrow(() -> new RuntimeException("해당 username이 없습니다."));
        if (!memberDto.getPassword().equals(member.getPassword())) {
            bindingResult.reject("notMatchPassword");
        }
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // 로그인 성공시 세션 만듦
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, member);
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "redirect:/";
    }
}
