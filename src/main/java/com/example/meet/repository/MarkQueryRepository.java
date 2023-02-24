package com.example.meet.repository;

import com.example.meet.entity.Mark;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.meet.entity.QMark.*;

@Repository
public class MarkQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MarkQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Mark> searchMarksByTime(Integer untilTime) {
        List<Mark> findMarks = queryFactory
                .selectFrom(mark)
                .where(mark.startTime.lt(LocalDateTime.now().plusHours(untilTime)))
                .fetch();
        return findMarks;
    }
}
