package com.algosync.backend.domain.submission;

import com.algosync.backend.domain.submission.entity.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionJpaRepository extends JpaRepository<SubmissionEntity, Long> {
    Optional<SubmissionEntity> findFirstByUserIdAndProblemIdOrderByCreatedAtDescSubmissionIdDesc(Long userId, Long problemId);
}
