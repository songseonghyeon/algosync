package com.algosync.backend.domain.users;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.algosync.backend.domain.users.dto.UserDto;

/**
 * 사용자 조회와 등록을 위한 MyBatis 매퍼입니다.
 */
@Mapper
public interface UserRepository {
	List<UserDto> selectUser();
	UserDto selectOneUser(Long userId);
	void insertUserId(Long userId);
}
