package com.algosync.backend.domain.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.algosync.backend.domain.users.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 저장된 사용자 목록을 불러오는 컨트롤러입니다.
 */
@Slf4j
@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;

	public List<UserDto> selectTest() {
		return userRepository.selectUser();
	}

}
