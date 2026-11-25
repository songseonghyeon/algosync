package com.algosync.backend.domain.submission;

import com.algosync.backend.domain.review.dto.ReviewJobResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 제출 저장과 AI 리뷰 생성 및 조회를 위한 HTTP 요청을 처리합니다.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService subService;

    // 리뷰를 완료한 뒤 결과를 응답합니다.
    @PostMapping("/save")
    public ResponseEntity<ReviewResponseDto> insertSubmission(@RequestBody SubmissionDto submission) {
        return ResponseEntity.ok(subService.insertSubmission(submission));
    }

    // 요청을 접수하고 이후 상태 조회에 사용할 토큰을 응답합니다.
    @PostMapping("/save-async")
    public ResponseEntity<ReviewJobResponseDto> insertSubmissionAsync(@RequestBody SubmissionDto submission) {
        return ResponseEntity.accepted().body(subService.insertSubmissionAsync(submission));
    }

    // 토큰으로 리뷰의 진행 상태와 결과를 조회합니다.
    @GetMapping("/review")
    public ResponseEntity<ReviewJobResponseDto> getReview(@RequestParam String token) {
        return ResponseEntity.ok(subService.getReview(token));
    }
}
