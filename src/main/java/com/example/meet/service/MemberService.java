package com.example.meet.service;

import com.example.meet.dto.MemberDto;
import com.example.meet.entity.Member;
import com.example.meet.dto.MemberSignupDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Long saveMember(MemberSignupDto memberSignupDto);

    MemberDto getMemberDto(Long memberId);
}
