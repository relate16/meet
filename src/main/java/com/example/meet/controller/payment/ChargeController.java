package com.example.meet.controller.payment;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.PaymentDto;
import com.example.meet.entity.Member;
import com.example.meet.entity.Payment;
import com.example.meet.repository.PaymentRepository;
import com.example.meet.service.MemberService;
import com.example.meet.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class ChargeController {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;
    private final PaymentService paymentService;

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
    @ResponseBody
    @PostMapping("/payment/chargeCash")
    public String chargeCash(@SessionAttribute(name = LOGIN_MEMBER) Member member,
                             @RequestBody PaymentDto paymentDto) {

        //결제자 member.id와 로그인된 member.id 같은지 확인
        if (member.getId() != paymentDto.getMemberId()) {
            throw new RuntimeException("충전할 사용자와 결제를 진행한 사용자의 id가 다릅니다.");
        }

        Payment payment = paymentService.savePayment(paymentDto);
        memberService.chargeCash(paymentDto.getMemberId(), payment.getAmount());
        return "ok";
    }
}
