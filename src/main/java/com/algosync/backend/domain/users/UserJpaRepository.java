package com.algosync.backend.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algosync.backend.domain.users.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
