package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import com.algosync.backend.global.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 리뷰 제공자를 선택하고 생성된 결과를 제출 ID와 함께 저장합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final GeminiService geminiService;
    private final NvidiaService nvidiaService;
    private final ReviewRepository reviewRepository;

    public ReviewResponseDto requestReview(Long submissionId, SubmissionDto dto, String prevCode) {
        try {
            ReviewResponseDto review = geminiService.requestReview(dto, prevCode);
            reviewRepository.insertReview(submissionId, review);
            return review;
        } catch (ExternalApiException e) {
            // Gemini가 일시적으로 응답할 수 없는 경우에만 NVIDIA로 재시도합니다.
            if (!"GEMINI_SERVICE_UNAVAILABLE".equals(e.getCode())) {
                throw e;
            }

            log.warn("Gemini unavailable. Falling back to NVIDIA.");
            ReviewResponseDto review = nvidiaService.requestReview(dto, prevCode);
            reviewRepository.insertReview(submissionId, review);
            return review;
        }
    }
}
