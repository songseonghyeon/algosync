package com.algosync.backend.domain.submission;

import com.algosync.backend.domain.problem.ProblemRepository;
import com.algosync.backend.domain.problem.dto.ProblemDto;
import com.algosync.backend.domain.review.GeminiService;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import com.algosync.backend.domain.users.dto.UserDto;
import com.algosync.backend.domain.users.dto.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmissionService {
	private final SubmissionRepository subRepo;
	private final ProblemRepository problemRepo;
	private final GeminiService gemService;
	private final UserService userService;

	public ReviewResponseDto insertSubmission(SubmissionDto dto) {
		UserDto userDto = userService.selectOneUser(dto.getUserId());
		if(userDto == null) {
			userService.insertUserId(dto.getUserId());
		}
		dto.setLanguage("JAVA");

		String prevCode = subRepo.getPrevCode(dto.getUserId(), dto.getProblemId());

		ProblemDto proDto = new ProblemDto();
		proDto.setId(dto.getProblemId());
		proDto.setTitle(dto.getProblemTitle());
		proDto.setLevel(dto.getLevel());
		proDto.setCategory(dto.getCategory());

		String isExist = problemRepo.selectTitle(proDto.getId());
		if(isExist == null) {
			log.info("해당 문제가 없습니다. DB에 insert 합니다.");
			problemRepo.insertProblem(proDto);
		}

		subRepo.insertSubmission(dto);

		return gemService.requestGem(dto, prevCode);
	}

	public Long selectUserId(String userEmail) {
		return subRepo.selectUserId(userEmail);
	}

	public void insertUserId(Long userId) {

	}
}
