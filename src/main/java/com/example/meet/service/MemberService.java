package com.example.meet.service;

import com.example.meet.dto.MemberSingupDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Long saveMember(MemberSingupDto memberSingupDto);
}
