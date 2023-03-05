package com.example.meet.service;

import com.example.meet.dto.PaymentDto;
import com.example.meet.entity.Member;
import com.example.meet.entity.Payment;
import com.example.meet.repository.MemberRepository;
import com.example.meet.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;


    @Override
    @Transactional
    public Payment savePayment(PaymentDto paymentDto) {
        Member findMember = memberService.getMember(paymentDto.getMemberId());
        Payment payment = new Payment(paymentDto.getPg(), paymentDto.getPayMethod(), paymentDto.getItemName(),
                paymentDto.getAmount(), findMember, paymentDto.isSuccess(), paymentDto.getErrorMsg(),
                paymentDto.getMerchantUid(), paymentDto.getImpUid());

        return paymentRepository.save(payment);
    }
}
