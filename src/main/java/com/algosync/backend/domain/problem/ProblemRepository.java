package com.algosync.backend.domain.problem;

import org.apache.ibatis.annotations.Mapper;

import com.algosync.backend.domain.problem.dto.ProblemDto;
import com.algosync.backend.domain.submission.dto.SubmissionDto;

@Mapper
public interface ProblemRepository {
	String selectTitle(Long id);
	void insertProblem(ProblemDto dto);
}
