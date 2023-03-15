package com.example.meet.controller;

import com.example.meet.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberInfoController {

    @GetMapping("/my-info")
    public String getMemberInfo(@ModelAttribute MemberDto memberDto) {
        return "my-info";
    }

    @PostMapping("/my-info/updateProfileImg")
    public String updateProfileImg(@ModelAttribute MemberDto memberDto) {
        return "/";
    }
}
