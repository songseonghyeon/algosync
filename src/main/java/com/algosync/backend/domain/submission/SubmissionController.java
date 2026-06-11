package com.algosync.backend.domain.submission;

import com.algosync.backend.domain.review.GeminiService;
import com.algosync.backend.domain.review.dto.GeminiResponseDto;
import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algosync.backend.domain.submission.dto.SubmissionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/submission")
@RequiredArgsConstructor
public class SubmissionController {
	private final SubmissionService subService;

	@PostMapping("/save")
	public ResponseEntity<ReviewResponseDto> insertSubmission(@RequestBody SubmissionDto submission) {
		System.out.println(submission);
		ReviewResponseDto result = subService.insertSubmission(submission);
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		return null;
	}
}
