package com.algosync.backend.domain.submission;

import com.algosync.backend.domain.review.GeminiService;
import com.algosync.backend.domain.review.dto.GeminiResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.algosync.backend.domain.problem.ProblemRepository;
import com.algosync.backend.domain.problem.dto.ProblemDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmissionService {
	private final SubmissionRepository subRepo;
	private final ProblemRepository problemRepo;
	private final GeminiService gemService;

	public ReviewResponseDto insertSubmission(SubmissionDto dto) {
		Long userId = selectUserId(dto.getUserEmail());
		dto.setUserId(userId);
		dto.setLanguage("JAVA");
		System.out.println(dto);

		ProblemDto proDto = new ProblemDto();
		proDto.setId(dto.getProblemId());
		proDto.setTitle(dto.getProblemTitle());
		proDto.setLevel(dto.getLevel());
		proDto.setCategory(dto.getCategory());
		String isExist = problemRepo.selectTitle(proDto.getId());
		if(isExist == null) {
			log.info("해당 문제가 없습니다. DB에 insert 합니다.");
			problemRepo.insertProblem(proDto);
			subRepo.insertSubmission(dto);
		} else {
			log.info("해당 문제가 있습니다. AI 검증으로 넘어갑니다.");
		}

		ReviewResponseDto result = gemService.requestGem(dto);
		if(result != null) {
			return result;
		}
		return null;
	}

	public Long selectUserId(String userEmail) {
		return subRepo.selectUserId(userEmail);
	}
}
