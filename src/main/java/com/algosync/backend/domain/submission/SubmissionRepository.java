package com.algosync.backend.domain.submission;

import org.apache.ibatis.annotations.Mapper;

import com.algosync.backend.domain.submission.dto.SubmissionDto;

@Mapper
public interface SubmissionRepository {
	void insertSubmission(SubmissionDto dto);
	Long selectUserId(String userEmail);
	String selectMyCode(SubmissionDto dto);
}
