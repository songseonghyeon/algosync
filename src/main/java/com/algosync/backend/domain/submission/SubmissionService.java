package com.algosync.backend.domain.submission;

import com.algosync.backend.domain.review.GeminiService;
import org.springframework.stereotype.Service;

import com.algosync.backend.domain.problem.ProblemRepository;
import com.algosync.backend.domain.problem.dto.ProblemDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {
	private final SubmissionRepository subRepo;
	private final ProblemRepository problemRepo;
	private final GeminiService gemService;

	public void insertSubmission(SubmissionDto dto) {
		Long userId = selectUserId(dto.getUserEmail());
		dto.setUserId(userId);
		dto.setLanguage("JAVA");
		System.out.println(dto);

		ProblemDto proDto = new ProblemDto();
		proDto.setId(dto.getProblemId());
		proDto.setTitle(dto.getProblemTitle());
		proDto.setLevel(dto.getLevel());
		proDto.setCategory(dto.getCategory());

		problemRepo.insertProblem(proDto);
		subRepo.insertSubmission(dto);

		gemService.requestGem(dto);
	}

	public Long selectUserId(String userEmail) {
		return subRepo.selectUserId(userEmail);
	}
}
