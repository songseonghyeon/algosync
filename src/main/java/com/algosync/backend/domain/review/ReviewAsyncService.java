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

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewAsyncService {
    private final GeminiService geminiService;

    // 제출 단위 토큰으로 상태를 들고 있어야 동일 문제의 다른 제출과 섞이지 않습니다.
    private final Map<String, ReviewJobResponseDto> reviewsByToken = new ConcurrentHashMap<>();

    public ReviewJobResponseDto createPendingJob(Long submissionId) {
        String reviewToken = UUID.randomUUID().toString();
        ReviewJobResponseDto pending = ReviewJobResponseDto.pending(submissionId, reviewToken);
        reviewsByToken.put(reviewToken, pending);
        return pending;
    }

    @Async
    public void requestReviewAsync(String reviewToken, Long submissionId, SubmissionDto dto, String prevCode) {
        try {
            ReviewResponseDto review = geminiService.requestGem(dto, prevCode);
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

    public ReviewJobResponseDto getReview(String reviewToken) {
        return reviewsByToken.get(reviewToken);
    }
}
