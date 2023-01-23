package com.example.meet.service;

import com.example.meet.entity.Mark;
import org.springframework.stereotype.Service;
import com.example.meet.dto.MarkDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public interface MarkService {

    MarkDto getMarkDto(Mark mark);
    /**
     * List<Mark> marks -> List<MarkDto> markDots
     */
    List<MarkDto> getMarkDtos(List<Mark> marks);

    Mark addParticipant(Long markId, HttpServletResponse response) throws IOException;
}
