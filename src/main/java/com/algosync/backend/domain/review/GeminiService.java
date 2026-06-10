package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.GeminiRequestDto;
import com.algosync.backend.domain.review.dto.GeminiResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String GemKey;

    @Value("${gemini.api.url}")
    private String GemUrl;

    public String requestGem(SubmissionDto dto) {
        String text = """
            너는 프로그래머스 알고리즘 문제를 풀이한 Java 소스코드를 심사하고 리팩토링 제안을 주는 전문 백엔드 개발자이자 AI 튜터야.
            제출된 코드를 분석해서 시간 복잡도, 공간 복잡도, 개선 피드백, 그리고 100점 만점 기준의 점수를 매겨줘.
            
            [주의사항]
            너는 일절 다른 부연 설명이나 인사말을 하지 말고, 오직 아래의 JSON 포맷으로만 응답해야 해.
            
            [응답 JSON 포맷]
            {
              "timeComplexity": "시간복잡도 결과와 결과에 대한 이유와 설명",
              "spaceComplexity": "공간복잡도 결과와 결과에 대한 이유와 설명",
              "feedback": "상세한 줄글 피드백 내용",
              "score": 100
            }
            
            [프로그래머스 문제 이름]
            """ + dto.getProblemTitle()
                +
            "[제출된 자바 소스코드]" + dto.getCode()
            ;
        // text로 Request 생성완료
        GeminiRequestDto gemReq = new GeminiRequestDto(text);

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
                System.out.println(response);
                //System.out.println(response.getCandidates().get(0).getContents().get(0).getParts().get(0).getText());
            }

        } catch(Exception e) {
            System.out.println("AI 리뷰 중 오류가 발생하였습니다!!!!! ");
            System.out.println(e);
        }
        return "전송완료";
    }
}
