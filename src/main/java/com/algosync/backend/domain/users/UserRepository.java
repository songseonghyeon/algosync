package com.algosync.backend.domain.users;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.algosync.backend.domain.users.dto.UserDto;

@Mapper
public interface UserRepository {
	List<UserDto> selectUser();
}
