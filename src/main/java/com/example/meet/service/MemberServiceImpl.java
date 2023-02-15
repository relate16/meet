package com.example.meet.service;

import com.example.meet.dto.MemberSignupDto;
import com.example.meet.entity.Member;
import com.example.meet.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
