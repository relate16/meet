package com.example.meet.controller;

import com.example.meet.dto.MarkDto;
import com.example.meet.entity.Mark;
import com.example.meet.repository.MarkRepository;
import com.example.meet.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final MarkRepository markRepository;
    private final MarkService markService;

    @GetMapping("/")
    public String showMap(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "meet-map";
    }

    @ResponseBody
    @PostMapping("/get-marks")
    public List<MarkDto> getMarks() {
        List<Mark> marks = markRepository.findAll();
        List<MarkDto> markDtos = markService.getMarkDtos(marks);
        return markDtos;
    }

}
