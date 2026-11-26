package com.algosync.backend.domain.users;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import com.algosync.backend.domain.users.dto.UserDto;
import com.algosync.backend.domain.users.entity.UserEntity;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 ID로 등록 여부를 조회하고 새 사용자를 등록합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserJpaRepository userJpaRepo;

	public void insertUserId(Long userId) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userId);
		userJpaRepo.save(userEntity);
	}

	public UserEntity selectOneUser(Long userId) {
		UserEntity userEntity = userJpaRepo.findById(userId).orElse(null);
		return userEntity;
	}
}
