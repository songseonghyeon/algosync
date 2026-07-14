package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import com.algosync.backend.global.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final GeminiService geminiService;
    private final NvidiaService nvidiaService;

    public ReviewResponseDto requestReview(SubmissionDto dto, String prevCode) {
        try {
            return geminiService.requestReview(dto, prevCode);
        } catch (ExternalApiException e) {
            if (!"GEMINI_SERVICE_UNAVAILABLE".equals(e.getCode())) {
                throw e;
            }

            log.warn("Gemini unavailable. Falling back to NVIDIA.");
            return nvidiaService.requestReview(dto, prevCode);
        }
    }
}
