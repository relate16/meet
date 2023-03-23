package com.example.meet.service;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.form.MemberUploadDto;
import com.example.meet.entity.Member;
import com.example.meet.dto.form.MemberSignupDto;
import com.example.meet.upload.domain.UploadFile;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Long saveMember(MemberSignupDto memberSignupDto);

    Member updateMember(Long memberId, Member updateMember);

    Member getMember(Long memberId);

    Member chargeCash(Long memberId, int chargeAmount);

    Member updateProfile(Long memberId, MemberUploadDto memberUploadDto);

    MemberDto getMemberDto(Long memberId);
}
