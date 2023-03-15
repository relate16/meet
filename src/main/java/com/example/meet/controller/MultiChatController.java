package com.example.meet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MultiChatController {

    @GetMapping("/multi-chat")
    public String GetMultiChat() {
        log.info("multi-chat start");
        return "multi-chat";
    }
}
