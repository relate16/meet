package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import com.example.meet.dto.form.MemberUploadDto;
import com.example.meet.upload.FileStore;
import com.example.meet.upload.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MemberInfoController {

    private final FileStore fileStore;

    @GetMapping("/my-info")
    public String getMemberInfo(@ModelAttribute MemberDto memberDto) {
        return "my-info";
    }

    @PostMapping("/my-info/updateProfileImg")
    @ResponseBody
    public String updateProfileImg(@RequestBody MemberUploadDto memberUploadDto) throws IOException {

        UploadFile uploadFile = fileStore.storeFile(memberUploadDto.getProfileImgFile());
        //여기서부터하기. 230316

        return "200";
    }
}
