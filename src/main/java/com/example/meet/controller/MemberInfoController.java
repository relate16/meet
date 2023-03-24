package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.form.MemberUploadDto;
import com.example.meet.entity.Member;
import com.example.meet.service.MemberService;
import com.example.meet.upload.FileStore;
import com.example.meet.upload.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import static com.example.meet.constants.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class MemberInfoController {

    private final FileStore fileStore;
    private final MemberService memberService;

    @GetMapping("/my-info")
    public String getMemberInfo(@ModelAttribute MemberUploadDto memberUploadDto,
                                @SessionAttribute(name = LOGIN_MEMBER) Member member,
                                Model model) {
        MemberDto getMemberDto = memberService.getMemberDto(member.getId());

        //'프로필 사진 수정' 버튼 누를 시 임시로 보여줄 img 파일 경로 설정.
        // 임시로 쓸 것이기 때문에 member 에 img 경로 저장x
        if (memberUploadDto.getUploadFilename() != null || memberUploadDto.getStoreFilename() != null) {
            UploadFile uploadFile =
                    new UploadFile(memberUploadDto.getUploadFilename(), memberUploadDto.getStoreFilename());
            getMemberDto.setProfileImg(uploadFile);
        }

        model.addAttribute("memberDto", getMemberDto);
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "my-info";
    }

    @ResponseBody
    @PostMapping("/my-info/update-profile-img")
    public MemberUploadDto updateProfileImg(@RequestBody MemberUploadDto memberUploadDto,
                                   @SessionAttribute(name = LOGIN_MEMBER) Member member) {

        //이미지 파일은 @PostMapping("/my-info/temp-profile-img")단계에서 이미 서버단에 저장
        //따라서 이미지 파일 경로만 DB에 저장
        Member updateMember = memberService.updateProfile(member.getId(), memberUploadDto);

        MemberUploadDto result = new MemberUploadDto(updateMember.getUsername(), updateMember.getAge(),
                updateMember.getGender(), updateMember.getCash(),
                updateMember.getProfileImgFile().getUploadFilename(),
                updateMember.getProfileImgFile().getStoreFilename(),
                null);

        return result;
    }

    /**
     * view 랜더링시 img 태그 src 경로로 접속해, 이미지를 다운받게 해주는 메서드
     * (랜더링 후 img 를 보여주게 하는 역할을 하는 메서드)
     * @throws MalformedURLException
     */
    @ResponseBody
    @GetMapping("/my-info/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        String fullPath = fileStore.getFullPath(filename);
        return new UrlResource("file:" + fullPath);
        // URLResource는 해당 내부 파일에 직접 접근해서 파일을 반환해준다.
        // 보완에 취약해 체크 로직을 넣어주는 게 좋다.
    }

    @ResponseBody
    @PostMapping("/my-info/temp-profile-img")
    public UploadFile tempProfileImg(@RequestParam("profileImgFile") MultipartFile multipartFile) throws IOException {
        return fileStore.storeFile(multipartFile); // 이미지 파일 서버단에 저장
    }
}
