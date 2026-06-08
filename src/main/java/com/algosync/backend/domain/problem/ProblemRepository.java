package com.algosync.backend.domain.problem;

import org.apache.ibatis.annotations.Mapper;

import com.algosync.backend.domain.submission.dto.SubmissionDto;

@Mapper
public interface ProblemRepository {
	void insertProblem(SubmissionDto dto);
}
