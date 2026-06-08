package com.algosync.backend.domain.submission;

import org.springframework.stereotype.Service;

import com.algosync.backend.domain.problem.ProblemRepository;
import com.algosync.backend.domain.submission.dto.SubmissionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {
	private final SubmissionRepository subRepo;
	private final ProblemRepository problemRepo;

	public void insertSubmission(SubmissionDto dto) {
		Long userId = selectUserId(dto.getUserEmail());
		dto.setUserId(userId);
		dto.setLanguage("JAVA");
		problemRepo.insertProblem(dto);
		subRepo.insertSubmission(dto);
	}

	public Long selectUserId(String userEmail) {
		return subRepo.selectUserId(userEmail);
	}
}
