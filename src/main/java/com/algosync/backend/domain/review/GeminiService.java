package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.GeminiRequestDto;
import com.algosync.backend.domain.review.dto.GeminiResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String GemKey;

    @Value("${gemini.api.url}")
    private String GemUrl;

    public ReviewResponseDto requestGem(SubmissionDto dto, String prevCode) {
        String prompt;
        if(prevCode == null || prevCode.isBlank()) {
            log.info("이전 제출 이력이 없습니다.");
            prompt = createBasicReviewPrompt(dto);
        } else {
            log.info("이전 제출 이력이 있습니다.");
            prompt = createCompareReviewPrompt(dto, prevCode);
        }

        GeminiRequestDto gemReq = new GeminiRequestDto(prompt);
        RestClient restClient = RestClient.builder().build();
        String url = GemUrl + "?key=" + GemKey;

        try {
            GeminiResponseDto response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(gemReq)
                    .retrieve()
                    .body(GeminiResponseDto.class);

            if(response != null && !response.getCandidates().isEmpty()) {
                log.info("제미나이에게 응답을 받았습니다.");
                String text = response.getCandidates().get(0).getContent().getParts().get(0).getText();
                return objectMapper.readValue(text, ReviewResponseDto.class);
            }
        } catch(Exception e) {
            log.error("AI 리뷰 중 오류가 발생했습니다.", e);
        }
        return null;
    }

    private String createBasicReviewPrompt(SubmissionDto dto) {
        return """
            너는 알고리즘 코딩 테스트 리뷰어다.
            아래 제출 코드를 분석해서 반드시 JSON 객체 하나만 반환하라.
            설명 문장, 머리말, 코드블록 마크다운(```), 추가 텍스트는 절대 포함하지 마라.

            평가 기준:
            1. 시간 복잡도 분석의 정확성
            2. 공간 복잡도 분석의 정확성
            3. 문제 해결 방식의 적절성
            4. 코드 가독성 및 유지보수성
            5. 예외 처리 및 경계값 처리
            6. 더 나은 알고리즘 또는 구현 방식 제안

            반드시 아래 JSON 스키마만 지켜라:
            {
              "timeComplexity": "Big-O 표기와 이유",
              "spaceComplexity": "Big-O 표기와 이유",
              "feedback": "장점, 문제점, 개선 방향을 포함한 구체적인 한국어 피드백",
              "score": "0"
            }

            주의:
            - score는 반드시 0~100 사이의 정수 문자열이어야 한다.
            - feedback은 반드시 한국어로 작성한다.
            - timeComplexity와 spaceComplexity에는 반드시 Big-O 표기를 포함한다.
            - 응답은 반드시 JSON 객체 하나만 반환한다.

            [프로그래머스 문제 이름]
            %s

            [제출된 자바 소스코드]
            %s
            """.formatted(dto.getProblemTitle(), dto.getCode());
    }

    private String createCompareReviewPrompt(SubmissionDto dto, String prevCode) {
        return """
            너는 알고리즘 코딩 테스트 리뷰어다.
            이전 제출 코드와 현재 제출 코드를 비교해서 반드시 JSON 객체 하나만 반환하라.
            설명 문장, 머리말, 코드블록 마크다운(```), 추가 텍스트는 절대 포함하지 마라.

            평가 기준:
            1. 현재 코드의 시간 복잡도와 공간 복잡도
            2. 이전 코드 대비 개선된 점
            3. 이전 코드 대비 나빠진 점 또는 새로 생긴 버그 가능성
            4. 문제 해결 방식의 적절성
            5. 코드 가독성 및 유지보수성
            6. 더 나은 알고리즘 또는 구현 방식 제안

            반드시 아래 JSON 스키마만 지켜라:
            {
              "timeComplexity": "현재 코드의 Big-O 표기와 이유",
              "spaceComplexity": "현재 코드의 Big-O 표기와 이유",
              "feedback": "이전 코드와 현재 코드를 비교한 구체적인 한국어 피드백",
              "score": "0"
            }

            주의:
            - score는 반드시 0~100 사이의 정수 문자열이어야 한다.
            - feedback은 반드시 한국어로 작성한다.
            - timeComplexity와 spaceComplexity에는 반드시 Big-O 표기를 포함한다.
            - 응답은 반드시 JSON 객체 하나만 반환한다.

            [프로그래머스 문제 이름]
            %s

            [이전 제출 자바 소스코드]
            %s

            [현재 제출 자바 소스코드]
            %s
            """.formatted(dto.getProblemTitle(), prevCode, dto.getCode());
    }
}
