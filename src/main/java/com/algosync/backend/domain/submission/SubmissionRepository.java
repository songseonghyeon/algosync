package com.algosync.backend.domain.submission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.algosync.backend.domain.submission.dto.SubmissionDto;

/**
 * 제출 저장과 기존 코드 조회를 위한 MyBatis 매퍼입니다.
 */
@Mapper
public interface SubmissionRepository {
	void insertSubmission(SubmissionDto dto);
	String selectMyCode(SubmissionDto dto);
	String getPrevCode(@Param("userId") Long userId, @Param("problemId") Long problemId);
}
