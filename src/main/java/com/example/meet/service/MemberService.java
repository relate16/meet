package com.example.meet.service;

import com.example.meet.dto.MemberSignupDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Long saveMember(MemberSignupDto memberSignupDto);
}
