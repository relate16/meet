package com.example.meet.service;

import com.example.meet.dto.MemberSingupDto;
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
    public Long saveMember(MemberSingupDto memberSingupDto) {
        memberSingupDto.setCash(0);
        Member member = new Member(memberSingupDto.getUsername(), memberSingupDto.getPassword(),
                memberSingupDto.getAge(), memberSingupDto.getGender(), memberSingupDto.getCash());
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}
