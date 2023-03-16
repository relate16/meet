package com.example.meet.service;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.form.MemberSignupDto;
import com.example.meet.entity.Member;
import com.example.meet.repository.MemberRepository;
import com.example.meet.upload.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long saveMember(MemberSignupDto memberSignupDto) {
        memberSignupDto.setCash(0);
        Member member = new Member(memberSignupDto.getUsername(), memberSignupDto.getPassword(),
                memberSignupDto.getAge(), memberSignupDto.getGender(), memberSignupDto.getCash());
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Override
    public Member getMember(Long memberId) {
        return findMemberById(memberId);
    }

    @Override
    @Transactional
    public Member chargeCash(Long memberId, int chargeAmount) {
        Member findMember = findMemberById(memberId);
        findMember.chargeCash(chargeAmount);
        return findMember;
    }

    @Override
    @Transactional
    public Member updateProfileImgFile(Long memberId, UploadFile uploadFile) {
        Member findMember = findMemberById(memberId);
        findMember.updateProfileImgFile(uploadFile);
        return findMember;
    }

    // ↓ dto 관련 메서드
    @Override
    public MemberDto getMemberDto(Long memberId) {
        Member member = findMemberById(memberId);
        MemberDto memberDto = new MemberDto(member.getId(), member.getUsername(), member.getPassword(),
                member.getAge(), member.getGender(), member.getCash());
        return memberDto;
    }

    private Member findMemberById(Long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Member findMember = memberOpt.orElseThrow(() -> new RuntimeException("id에 해당하는 member가 없습니다"));
        return findMember;
    }
}
