package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.form.MemberUploadDto;
import com.example.meet.entity.Member;
import com.example.meet.service.MemberService;
import com.example.meet.upload.FileStore;
import com.example.meet.upload.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class MemberInfoController {

    private final FileStore fileStore;
    private final MemberService memberService;

    @GetMapping("/my-info")
    public String getMemberInfo(@ModelAttribute MemberDto memberDto,
                                @SessionAttribute(name = LOGIN_MEMBER) Member member,
                                Model model) {
        MemberDto getMemberDto = memberService.getMemberDto(member.getId());
        model.addAttribute("memberDto", getMemberDto);
        return "my-info";
    }

    @PostMapping("/my-info/updateProfileImg")
    public Member updateProfileImg(@RequestParam("file") MultipartFile multipartFile,
                                   @SessionAttribute(name = LOGIN_MEMBER) Member member) throws IOException {
        //500오류 뜸 일단 https://cloud0477.tistory.com/122 참고
        System.out.println("formData = " + multipartFile);
        
        //이미지 파일 서버단에 저장
        UploadFile uploadFile = fileStore.storeFile(multipartFile);

        //이미지 파일 경로 DB에 저장
        Member updateMember = memberService.updateProfileImgFile(member.getId(), uploadFile);

        return updateMember;
    }
}
