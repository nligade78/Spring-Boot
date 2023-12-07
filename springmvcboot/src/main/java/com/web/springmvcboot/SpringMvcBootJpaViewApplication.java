package com.web.springmvcboot;

import com.web.springmvcboot.entity.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMvcBootJpaViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcBootJpaViewApplication.class, args);

		Student s=new Student();
		s.setName("Nikhil");
		System.out.println(s.getName());

	}

}
