package com.algosync.backend.domain.review;

import com.algosync.backend.domain.submission.dto.SubmissionDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewPromptFactory {

    public String createPrompt(SubmissionDto dto, String prevCode) {
        if (prevCode == null || prevCode.isBlank()) {
            return createBasicReviewPrompt(dto);
        }
        return createCompareReviewPrompt(dto, prevCode);
    }

    private String createBasicReviewPrompt(SubmissionDto dto) {
        return """
            너는 알고리즘 코드 리뷰어다.
            반드시 JSON 객체 하나만 반환하라.
            설명 문장, 마크다운, 코드블록, 추가 텍스트는 포함하지 마라.

            평가 원칙:
            - score는 현재 제출 코드만 보고 절대평가한다.
            - 점수는 반드시 항목별 점수 합으로 계산한다.
            - 점수는 0~100 사이의 정수 문자열로 반환한다.

            총점 100점, 항목별 배점:
            - correctness: 40점
            - timeComplexityFit: 20점
            - spaceComplexityFit: 10점
            - edgeCaseHandling: 15점
            - readabilityMaintainability: 15점

            채점 기준:
            1. correctness (0~40)
            - 문제를 정확히 해결할 가능성이 매우 높으면 36~40점
            - 핵심 아이디어는 맞지만 일부 반례 가능성이 있으면 24~35점
            - 핵심 로직에 의미 있는 결함이 있으면 10~23점
            - 오답 가능성이 매우 높거나 접근이 잘못되었으면 0~9점

            2. timeComplexityFit (0~20)
            - 문제 수준에 적절한 최적 또는 준최적 복잡도면 17~20점
            - 동작은 가능하지만 비효율이 보이면 9~16점
            - 비현실적으로 비효율적이면 0~8점

            3. spaceComplexityFit (0~10)
            - 메모리 사용이 적절하면 8~10점
            - 다소 비효율적이면 4~7점
            - 불필요한 메모리 사용이 크면 0~3점

            4. edgeCaseHandling (0~15)
            - 경계값과 예외 상황 처리가 안정적이면 12~15점
            - 일부만 고려되면 6~11점
            - 반례에 취약하면 0~5점

            5. readabilityMaintainability (0~15)
            - 변수명, 메서드 구조, 분기 흐름이 명확하면 12~15점
            - 이해는 가능하지만 구조가 거칠면 6~11점
            - 읽기 어렵고 유지보수가 어렵다면 0~5점

            강제 규칙:
            - correctness가 23점 이하이면 총점은 최대 69점이다.
            - correctness가 9점 이하이면 총점은 최대 39점이다.
            - 실제 코드 근거 없이 점수를 후하게 주지 마라.
            - score는 항목별 점수 합과 반드시 일치해야 한다.
            - 총점 계산 후 임의로 보정하지 마라.
            - timeComplexity와 spaceComplexity는 반드시 Big-O 표기를 포함하라.
            - feedback은 반드시 한국어로 4~7문장으로 작성하라.
            - feedback에는 전체 평가, 정답 가능성 또는 핵심 로직 근거, 시간복잡도/공간복잡도 근거,
              경계값 또는 반례 가능성, 가장 중요한 개선점 1개 이상을 포함하라.

            반드시 아래 JSON 스키마만 지켜라:
            {
              "timeComplexity": "현재 코드의 Big-O 표기와 이유",
              "spaceComplexity": "현재 코드의 Big-O 표기와 이유",
              "feedback": "현재 코드의 점수 근거와 개선 방향을 설명하는 한국어 리뷰",
              "score": "78"
            }

            [프로그래머스 문제 이름]
            %s

            [현재 제출 자바 테스트코드]
            %s
            """.formatted(dto.getProblemTitle(), dto.getCode());
    }

    private String createCompareReviewPrompt(SubmissionDto dto, String prevCode) {
        return """
            너는 알고리즘 코드 리뷰어다.
            반드시 JSON 객체 하나만 반환하라.
            설명 문장, 마크다운, 코드블록, 추가 텍스트는 포함하지 마라.

            평가 원칙:
            - score는 현재 제출 코드만 보고 절대평가한다.
            - 이전 제출 코드가 있더라도 현재 score는 이전 코드의 영향 없이 계산한다.
            - 이전 제출 코드는 feedback에서만 비교한다.
            - 점수는 반드시 항목별 점수 합으로 계산한다.
            - 점수는 0~100 사이의 정수 문자열로 반환한다.

            총점 100점, 항목별 배점:
            - correctness: 40점
            - timeComplexityFit: 20점
            - spaceComplexityFit: 10점
            - edgeCaseHandling: 15점
            - readabilityMaintainability: 15점

            채점 기준:
            1. correctness (0~40)
            - 문제를 정확히 해결할 가능성이 매우 높으면 36~40점
            - 핵심 아이디어는 맞지만 일부 반례 가능성이 있으면 24~35점
            - 핵심 로직에 의미 있는 결함이 있으면 10~23점
            - 오답 가능성이 매우 높거나 접근이 잘못되었으면 0~9점

            2. timeComplexityFit (0~20)
            - 문제 수준에 적절한 최적 또는 준최적 복잡도면 17~20점
            - 동작은 가능하지만 비효율이 보이면 9~16점
            - 비현실적으로 비효율적이면 0~8점

            3. spaceComplexityFit (0~10)
            - 메모리 사용이 적절하면 8~10점
            - 다소 비효율적이면 4~7점
            - 불필요한 메모리 사용이 크면 0~3점

            4. edgeCaseHandling (0~15)
            - 경계값과 예외 상황 처리가 안정적이면 12~15점
            - 일부만 고려되면 6~11점
            - 반례에 취약하면 0~5점

            5. readabilityMaintainability (0~15)
            - 변수명, 메서드 구조, 분기 흐름이 명확하면 12~15점
            - 이해는 가능하지만 구조가 거칠면 6~11점
            - 읽기 어렵고 유지보수가 어렵다면 0~5점

            강제 규칙:
            - correctness가 23점 이하이면 총점은 최대 69점이다.
            - correctness가 9점 이하이면 총점은 최대 39점이다.
            - 실제 코드 근거 없이 점수를 후하게 주지 마라.
            - score는 항목별 점수 합과 반드시 일치해야 한다.
            - 총점 계산 후 임의로 보정하지 마라.
            - timeComplexity와 spaceComplexity는 반드시 Big-O 표기를 포함하라.
            - feedback은 반드시 한국어로 4~7문장으로 작성하라.
            - feedback에는 전체 평가, 정답 가능성 또는 핵심 로직 근거, 시간복잡도/공간복잡도 근거,
              경계값 또는 반례 가능성, 가장 중요한 개선점 1개 이상을 포함하라.
            - feedback 마지막 문장에는 이전 제출 대비 개선점, 유지된 문제점, 또는 새로 생긴 문제점을 포함하라.
            - 이전보다 나아졌거나 나빠졌다는 이유만으로 현재 score를 조정하지 마라.

            반드시 아래 JSON 스키마만 지켜라:
            {
              "timeComplexity": "현재 코드의 Big-O 표기와 이유",
              "spaceComplexity": "현재 코드의 Big-O 표기와 이유",
              "feedback": "현재 코드의 점수 근거와 이전 제출 대비 비교를 설명하는 한국어 리뷰",
              "score": "78"
            }

            [프로그래머스 문제 이름]
            %s

            [이전 제출 자바 테스트코드]
            %s

            [현재 제출 자바 테스트코드]
            %s
            """.formatted(dto.getProblemTitle(), prevCode, dto.getCode());
    }
}
