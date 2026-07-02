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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService subService;

    @PostMapping("/save")
    public ResponseEntity<ReviewResponseDto> insertSubmission(@RequestBody SubmissionDto submission) {
        return ResponseEntity.ok(subService.insertSubmission(submission));
    }

    @PostMapping("/save-async")
    public ResponseEntity<ReviewJobResponseDto> insertSubmissionAsync(@RequestBody SubmissionDto submission) {
        return ResponseEntity.accepted().body(subService.insertSubmissionAsync(submission));
    }

    @GetMapping("/review")
    public ResponseEntity<ReviewJobResponseDto> getReview(@RequestParam String token) {
        return ResponseEntity.ok(subService.getReview(token));
    }
}
