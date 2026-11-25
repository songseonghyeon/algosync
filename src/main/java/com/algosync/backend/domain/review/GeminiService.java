package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import com.algosync.backend.global.exception.ExternalApiException;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

/**
 * Gemini에 코드 리뷰를 요청하고 응답을 리뷰 결과로 변환합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {
    private final ObjectMapper objectMapper;
    private final ReviewPromptFactory reviewPromptFactory;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.model}")
    private String modelName;

    // 제출 내용으로 프롬프트를 만들고 Gemini의 응답 JSON을 리뷰 객체로 변환합니다.
    public ReviewResponseDto requestReview(SubmissionDto dto, String prevCode) {
        try {
            String response = buildModel().chat(reviewPromptFactory.createPrompt(dto, prevCode));
            return objectMapper.readValue(response, ReviewResponseDto.class);
        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Gemini review request failed", e);
            if (isServiceUnavailable(e)) {
                throw ExternalApiException.geminiServiceUnavailable(e);
            }
            throw ExternalApiException.geminiReviewFailed(e);
        }
    }

    private ChatModel buildModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }

    // 원인 예외까지 확인해 메시지에 HTTP 503이 들어 있는지 검사합니다.
    private boolean isServiceUnavailable(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            String message = current.getMessage();
            if (message != null && message.contains("503")) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
