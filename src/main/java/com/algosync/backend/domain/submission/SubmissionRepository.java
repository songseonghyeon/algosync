package com.algosync.backend.domain.submission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.algosync.backend.domain.submission.dto.SubmissionDto;

@Mapper
public interface SubmissionRepository {
	void insertSubmission(SubmissionDto dto);
	String selectMyCode(SubmissionDto dto);
	String getPrevCode(@Param("userId") Long userId, @Param("problemId") Long problemId);
}
