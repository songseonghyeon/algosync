package com.algosync.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring Boot 애플리케이션을 시작하고 비동기 실행을 활성화합니다.
 */
@SpringBootApplication
@EnableAsync
public class AlgosyncApplication {

	public static void main(String[] args) {
		// 실행 인자를 Spring Boot에 전달해 활성 프로필과 외부 설정을 적용합니다.
		SpringApplication.run(AlgosyncApplication.class, args);
	}
}
