package com.algosync.backend.domain.submission.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDto {
	private Long id;
	private Long userId;        // 어떤 유저가 풀었는지
	private Long problemId;     // 어떤 문제를 풀었는지
	private String language;    // 제출 언어 (Java 등)
	private String code;        // 제출한 소스 코드
	private Integer solveTime;  // 소요 시간
	private boolean isSuccess;  // 성공 여부 (true/false)
	private LocalDateTime createdAt;

	// 크롬 확장 프로그램에서 요청을 받을 때 매핑하기 위한 필드들
	private String userEmail;   // 이메일로 유저를 식별하기 위함
	private String problemTitle;// 문제 제목
	private Integer level;      // 문제 난이도
	private String category;    // 알고리즘 유형
}
