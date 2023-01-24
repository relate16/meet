package com.example.meet.repository;

import com.example.meet.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findByUsername(String username);

    List<Mark> findMarksByEndTimeBefore(LocalDateTime now);
    List<Mark> findMarksByEndTimeAfter(LocalDateTime now);

}
