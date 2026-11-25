package com.algosync.backend.domain.problem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 문제 ID로 저장된 문제의 제목을 조회합니다.
 */
@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemJpaRepository problemJpaRepository;

    public String selectTitle(Long problemId) {
        // 26.11.24
        // Mybatis에서 JPA로 전환
        //String result = problemRepository.selectTitle(problemId);
        String title = problemJpaRepository.findById(problemId)
            .map(problem -> problem.getTitle())
            .orElse(null);
        return title;
    }
}
