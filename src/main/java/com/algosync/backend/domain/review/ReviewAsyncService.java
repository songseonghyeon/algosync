package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.ReviewJobResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 백그라운드에서 리뷰를 실행하고 토큰별 진행 상태를 메모리에 보관합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewAsyncService {
    private final ReviewService reviewService;

    // 제출 단위 토큰으로 상태를 들고 있어야 동일 문제의 다른 제출과 섞이지 않습니다.
    private final Map<String, ReviewJobResponseDto> reviewsByToken = new ConcurrentHashMap<>();

    // 클라이언트가 조회할 토큰을 발급하고 작업을 대기 상태로 등록합니다.
    public ReviewJobResponseDto createPendingJob(Long submissionId) {
        String reviewToken = UUID.randomUUID().toString();
        ReviewJobResponseDto pending = ReviewJobResponseDto.pending(submissionId, reviewToken);
        reviewsByToken.put(reviewToken, pending);
        return pending;
    }

    // 별도 스레드에서 리뷰를 실행하고 결과에 따라 완료 또는 실패 상태를 기록합니다.
    @Async
    public void requestReviewAsync(String reviewToken, Long submissionId, SubmissionDto dto, String prevCode) {
        try {
            ReviewResponseDto review = reviewService.requestReview(submissionId, dto, prevCode);
            if (review == null) {
                reviewsByToken.put(reviewToken, ReviewJobResponseDto.failed(submissionId, reviewToken));
                return;
            }
            reviewsByToken.put(reviewToken, ReviewJobResponseDto.completed(submissionId, reviewToken, review));
        } catch (Exception e) {
            log.error("비동기 AI 리뷰 실패. submissionId={}", submissionId, e);
            reviewsByToken.put(reviewToken, ReviewJobResponseDto.failed(submissionId, reviewToken));
        }
    }

    // 토큰에 연결된 최신 상태를 반환하며, 등록되지 않은 토큰이면 null을 반환합니다.
    public ReviewJobResponseDto getReview(String reviewToken) {
        return reviewsByToken.get(reviewToken);
    }
}
