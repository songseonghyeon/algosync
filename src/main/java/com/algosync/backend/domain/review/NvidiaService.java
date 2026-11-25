package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import com.algosync.backend.global.exception.ExternalApiException;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

/**
 * OpenAI 호환 NVIDIA API에 코드 리뷰를 요청하고 응답을 변환합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NvidiaService {
    private final ObjectMapper objectMapper;
    private final ReviewPromptFactory reviewPromptFactory;

    @Value("${nvidia.api.key}")
    private String apiKey;

    @Value("${nvidia.api.model}")
    private String modelName;

    @Value("${nvidia.api.url}")
    private String baseUrl;

    public ReviewResponseDto requestReview(SubmissionDto dto, String prevCode) {
        try {
            String response = buildModel().chat(reviewPromptFactory.createPrompt(dto, prevCode));
            return objectMapper.readValue(response, ReviewResponseDto.class);
        } catch (Exception e) {
            log.error("NVIDIA review request failed", e);
            throw ExternalApiException.nvidiaReviewFailed(e);
        }
    }

    // 외부 설정의 API 키, 주소, 모델 이름으로 NVIDIA 클라이언트를 구성합니다.
    private ChatModel buildModel() {
        return OpenAiChatModel.builder()
                .apiKey(normalize(apiKey))
                .baseUrl(normalize(baseUrl))
                .modelName(normalize(modelName))
                .build();
    }

    // 설정값에 포함된 바깥쪽 큰따옴표와 공백을 제거합니다.
    private String normalize(String value) {
        if (value == null) {
            return "";
        }

        String trimmed = value.trim();
        if (trimmed.length() >= 2 && trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            return trimmed.substring(1, trimmed.length() - 1).trim();
        }
        return trimmed;
    }
}
