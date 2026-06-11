package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.GeminiRequestDto;
import com.algosync.backend.domain.review.dto.GeminiResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String GemKey;

    @Value("${gemini.api.url}")
    private String GemUrl;

    public ReviewResponseDto requestGem(SubmissionDto dto) {
        String prompt = """
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
            
            score 채점 기준:
            - 90~100: 매우 우수, 개선점이 적음
            - 70~89: 전반적으로 좋으나 개선 여지 있음
            - 50~69: 정답일 수 있으나 비효율 또는 품질 문제 존재
            - 0~49: 핵심 로직 또는 효율성에 큰 문제 존재
            
            반드시 아래 JSON 스키마를 지켜라:
            {
              "timeComplexity": "빅오 표기와 그 이유를 포함한 설명",
              "spaceComplexity": "빅오 표기와 그 이유를 포함한 설명",
              "feedback": "장점, 문제점, 개선 방향을 포함한 구체적인 한국어 피드백",
              "score": 0
            }
            
            주의:
            - score는 반드시 0~100 사이의 정수여야 한다.
            - feedback은 반드시 한국어로 작성한다.
            - timeComplexity와 spaceComplexity에는 반드시 Big-O 표기를 포함한다.
            - 응답은 반드시 JSON 객체 하나만 반환한다.
            
            [프로그래머스 문제 이름]
            %s
            
            [제출된 자바 소스코드]
            %s
            """.formatted(dto.getProblemTitle(), dto.getCode());
        // text로 Request 생성완료
        GeminiRequestDto gemReq = new GeminiRequestDto(prompt);

        RestClient restClient = RestClient.builder().build();

        String url = GemUrl + "?key=" + GemKey;
        try {
            GeminiResponseDto response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON) // 👈 JSON으로 보낸다고 명시해 주는 것이 안전합니다!
                    .body(gemReq)
                    .retrieve()                             // 👈 "구글 서버 호출해라"
                    .body(GeminiResponseDto.class);         // 👈 "받은 JSON을 이 DTO 객체로 변환해라"

            if(response != null && !response.getCandidates().isEmpty()) {
                System.out.println("제미나이에게 응답을 받았습니다.");
                String text = response.getCandidates().get(0).getContent().getParts().get(0).getText();
                ReviewResponseDto reviewResponse = objectMapper.readValue(text, ReviewResponseDto.class);
                return reviewResponse;
            }
        } catch(Exception e) {
            System.out.println("AI 리뷰 중 오류가 발생하였습니다!!!!! ");
            System.out.println(e);
        }
        return null;
    }
}
