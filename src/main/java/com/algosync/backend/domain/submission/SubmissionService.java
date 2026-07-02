package com.algosync.backend.domain.submission;

import com.algosync.backend.common.exception.ReviewJobNotFoundException;
import com.algosync.backend.common.exception.SubmissionProcessingException;
import com.algosync.backend.domain.problem.ProblemRepository;
import com.algosync.backend.domain.problem.dto.ProblemDto;
import com.algosync.backend.domain.review.GeminiService;
import com.algosync.backend.domain.review.ReviewAsyncService;
import com.algosync.backend.domain.review.dto.ReviewJobResponseDto;
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
    private final ReviewAsyncService reviewAsyncService;
    private final UserService userService;

    public ReviewResponseDto insertSubmission(SubmissionDto dto) {
        String prevCode = prepareSubmission(dto);
        subRepo.insertSubmission(dto);
        ReviewResponseDto review = gemService.requestGem(dto, prevCode);
        if (review == null) {
            throw new SubmissionProcessingException("AI 리뷰 생성에 실패했습니다.");
        }
        return review;
    }

    public ReviewJobResponseDto insertSubmissionAsync(SubmissionDto dto) {
        String prevCode = prepareSubmission(dto);
        subRepo.insertSubmission(dto);

        ReviewJobResponseDto pending = reviewAsyncService.createPendingJob(dto.getId());
        reviewAsyncService.requestReviewAsync(pending.getReviewToken(), dto.getId(), dto, prevCode);
        return pending;
    }

    public ReviewJobResponseDto getReview(String reviewToken) {
        ReviewJobResponseDto reviewJob = reviewAsyncService.getReview(reviewToken);
        if (reviewJob == null) {
            throw new ReviewJobNotFoundException("해당 토큰의 리뷰 작업을 찾을 수 없습니다.");
        }
        return reviewJob;
    }

    public Long selectUserId(String userEmail) {
        return subRepo.selectUserId(userEmail);
    }

    private String prepareSubmission(SubmissionDto dto) {
        if (dto.getUserId() != null) {
            UserDto userDto = userService.selectOneUser(dto.getUserId());
            if (userDto == null) {
                userService.insertUserId(dto.getUserId());
            }
        }
        dto.setLanguage("JAVA");

        String prevCode = null;
        if (dto.getUserId() != null && dto.getProblemId() != null) {
            prevCode = subRepo.getPrevCode(dto.getUserId(), dto.getProblemId());
        }

        ProblemDto problemDto = new ProblemDto();
        problemDto.setId(dto.getProblemId());
        problemDto.setTitle(dto.getProblemTitle());
        problemDto.setLevel(dto.getLevel());
        problemDto.setCategory(dto.getCategory());
        problemDto.setId(dto.getProblemId());

        String existingTitle = problemRepo.selectTitle(problemDto.getId());
        if (existingTitle == null) {
            log.info("DB에서 문제를 찾을 수 없습니다. Insert problem metadata. problemId={}", problemDto.getId());
            problemRepo.insertProblem(problemDto);
        }

        return prevCode;
    }
}
