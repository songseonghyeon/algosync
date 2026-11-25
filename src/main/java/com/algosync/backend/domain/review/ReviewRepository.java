package com.algosync.backend.domain.review;

import com.algosync.backend.domain.review.dto.ReviewResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 제출에 연결된 AI 리뷰 결과를 저장하는 MyBatis 매퍼입니다.
 */
@Mapper
public interface ReviewRepository {
    void insertReview(@Param("submissionId") Long submissionId, @Param("review") ReviewResponseDto review);
}
