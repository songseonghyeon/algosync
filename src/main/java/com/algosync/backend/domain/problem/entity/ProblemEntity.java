package com.algosync.backend.domain.problem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "problems")
public class ProblemEntity {
	@Id
	private long problemId;
	private String title;
	private int level;
	private String category;
}
