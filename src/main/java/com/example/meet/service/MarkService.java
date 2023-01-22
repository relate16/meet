package com.example.meet.service;

import com.example.meet.entity.Mark;
import org.springframework.stereotype.Service;
import com.example.meet.dto.MarkDto;

import java.util.List;

@Service
public interface MarkService {

    MarkDto getMarkDto(Mark mark);
    /**
     * List<Mark> marks -> List<MarkDto> markDots
     */
    List<MarkDto> getMarkDtos(List<Mark> marks);

}
