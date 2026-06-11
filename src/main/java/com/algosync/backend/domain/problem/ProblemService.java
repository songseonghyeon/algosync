package com.algosync.backend.domain.problem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public String selectTitle(Long id) {
        String result = problemRepository.selectTitle(id);
        return result;
    }
}
