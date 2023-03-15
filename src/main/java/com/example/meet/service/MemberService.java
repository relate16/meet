package com.example.meet.service;

import com.example.meet.dto.MemberDto;
import com.example.meet.entity.Member;
import com.example.meet.dto.form.MemberSignupDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Long saveMember(MemberSignupDto memberSignupDto);

    Member getMember(Long memberId);

    Member chargeCash(Long memberId, int chargeAmount);

    MemberDto getMemberDto(Long memberId);
}
