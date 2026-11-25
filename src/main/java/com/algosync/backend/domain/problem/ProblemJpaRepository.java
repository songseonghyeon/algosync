package com.algosync.backend.domain.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algosync.backend.domain.problem.entity.ProblemEntity;

public interface ProblemJpaRepository extends JpaRepository<ProblemEntity, Long> {
}
