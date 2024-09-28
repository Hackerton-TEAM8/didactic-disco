package com.team8.timeCapsule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TimeCapsuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeCapsuleApplication.class, args);
	}

}
