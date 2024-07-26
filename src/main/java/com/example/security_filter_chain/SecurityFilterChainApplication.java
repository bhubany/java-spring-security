package com.example.security_filter_chain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.security_filter_chain.*")
public class SecurityFilterChainApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityFilterChainApplication.class, args);
	}

}
