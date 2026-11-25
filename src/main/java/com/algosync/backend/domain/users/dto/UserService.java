package com.algosync.backend.domain.users.dto;

import org.springframework.stereotype.Service;

import com.algosync.backend.domain.users.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 ID로 등록 여부를 조회하고 새 사용자를 등록합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepo;

	public void insertUserId(Long userId) {
		userRepo.insertUserId(userId);
	}

	public UserDto selectOneUser(Long userId) {
		return userRepo.selectOneUser(userId);
	}
}
