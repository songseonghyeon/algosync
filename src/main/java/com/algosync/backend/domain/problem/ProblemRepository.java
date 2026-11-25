package com.algosync.backend.domain.problem;

import org.apache.ibatis.annotations.Mapper;

import com.algosync.backend.domain.problem.dto.ProblemDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;

/**
 * 문제 제목 조회와 메타데이터 저장을 위한 MyBatis 매퍼입니다.
 */
@Mapper
public interface ProblemRepository {
	String selectTitle(Long problemId);
	void insertProblem(ProblemDto dto);
}
