package com.example.meet.controller.payment;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.PaymentDto;
import com.example.meet.entity.Member;
import com.example.meet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class ChargeController {

    private final MemberService memberService;

    @GetMapping("/payment/chargeCash")
    public String payToChargeCash(@SessionAttribute(name = LOGIN_MEMBER) Member member, Model model) {
        MemberDto memberDto = memberService.getMemberDto(member.getId());
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("memberDto", memberDto);
        return "payment/charge";
    }

    /**
     * 결제 성공 후 캐시 충전
     */
    @PostMapping("/payment/chargeCash")
    public String chargeCash(@SessionAttribute(name = LOGIN_MEMBER) Member member,
                             @RequestBody PaymentDto paymentDto) {
        System.out.println("member = " + member);
        System.out.println("paymentDto = " + paymentDto);
        // payment 저장후 Member charge 로직 실행


    }
}
