package com.algosync.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.algosync.backend.domain.users.UserController;
import com.algosync.backend.domain.users.UserRepository;

@SpringBootApplication
@EnableAsync
public class AlgosyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgosyncApplication.class, args);
	}
}
