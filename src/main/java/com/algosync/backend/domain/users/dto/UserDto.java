package com.algosync.backend.domain.users.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 데이터를 전달하는 객체입니다.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long userId;
	private String email;
	private String name;
	private OffsetDateTime createdAt;
}
