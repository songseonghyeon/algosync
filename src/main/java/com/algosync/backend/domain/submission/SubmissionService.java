package com.algosync.backend.domain.submission;

import com.algosync.backend.common.exception.ReviewJobNotFoundException;
import com.algosync.backend.common.exception.SubmissionProcessingException;
import com.algosync.backend.domain.problem.ProblemJpaRepository;
import com.algosync.backend.domain.problem.ProblemRepository;
import com.algosync.backend.domain.problem.entity.ProblemEntity;
import com.algosync.backend.domain.review.ReviewAsyncService;
import com.algosync.backend.domain.review.ReviewService;
import com.algosync.backend.domain.review.dto.ReviewJobResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import com.algosync.backend.domain.users.dto.UserDto;
import com.algosync.backend.domain.users.UserService;
import com.algosync.backend.domain.users.entity.UserEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 제출에 필요한 데이터를 준비하고 저장한 뒤 AI 리뷰를 요청합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository subRepo;
    private final ProblemRepository problemRepo;
    private final ProblemJpaRepository problemJpaRepo;
    private final ReviewService reviewService;
    private final ReviewAsyncService reviewAsyncService;
    private final UserService userService;

    // 제출을 저장하고 AI 리뷰가 완료될 때까지 요청을 처리합니다.
    public ReviewResponseDto insertSubmission(SubmissionDto dto) {
        String prevCode = prepareSubmission(dto);
        subRepo.insertSubmission(dto);
        ReviewResponseDto review = reviewService.requestReview(dto.getSubmissionId(), dto, prevCode);
        if (review == null) {
            throw new SubmissionProcessingException("AI 리뷰 생성에 실패했습니다.");
        }
        return review;
    }

    // 제출을 저장한 뒤 백그라운드 리뷰를 시작하고 조회 토큰을 반환합니다.
    public ReviewJobResponseDto insertSubmissionAsync(SubmissionDto dto) {
        String prevCode = prepareSubmission(dto);
        subRepo.insertSubmission(dto);

        ReviewJobResponseDto pending = reviewAsyncService.createPendingJob(dto.getSubmissionId());
        reviewAsyncService.requestReviewAsync(pending.getReviewToken(), dto.getSubmissionId(), dto, prevCode);
        return pending;
    }

    // 발급된 토큰으로 리뷰 상태를 조회하고, 토큰이 없으면 예외를 발생시킵니다.
    public ReviewJobResponseDto getReview(String reviewToken) {
        ReviewJobResponseDto reviewJob = reviewAsyncService.getReview(reviewToken);
        if (reviewJob == null) {
            throw new ReviewJobNotFoundException("해당 토큰의 리뷰 작업을 찾을 수 없습니다.");
        }
        return reviewJob;
    }

    // 미등록 사용자와 문제를 저장하고 비교에 사용할 이전 코드를 찾습니다.
    private String prepareSubmission(SubmissionDto dto) {
        if (dto.getUserId() != null) {
            UserEntity userEntity = userService.selectOneUser(dto.getUserId());
            if(userEntity == null) {
                userService.insertUserId(dto.getUserId());
            }
        }

        // 새 제출을 저장하기 전에 조회해야 이전 제출 코드를 가져올 수 있습니다.
        String prevCode = null;
        if (dto.getUserId() != null && dto.getProblemId() != null) {
            prevCode = subRepo.getPrevCode(dto.getUserId(), dto.getProblemId());
        }

        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setProblemId(dto.getProblemId());
        problemEntity.setTitle(dto.getProblemTitle());
        problemEntity.setLevel(dto.getLevel());
        problemEntity.setCategory(dto.getCategory());

        if (!problemJpaRepo.existsById(dto.getProblemId())) {
            log.info("DB에서 문제를 찾을 수 없습니다. Insert problem metadata. problemId={}", dto.getProblemId());
            problemJpaRepo.save(problemEntity);
        }

        return prevCode;
    }
}
