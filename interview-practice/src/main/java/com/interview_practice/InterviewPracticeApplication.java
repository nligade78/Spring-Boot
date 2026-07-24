package com.interview_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.interview_practice"})
public class InterviewPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewPracticeApplication.class, args);
	}

}
