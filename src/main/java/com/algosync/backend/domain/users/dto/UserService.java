package com.algosync.backend.domain.users.dto;

import org.springframework.stereotype.Service;

import com.algosync.backend.domain.users.UserRepository;

import lombok.RequiredArgsConstructor;

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
