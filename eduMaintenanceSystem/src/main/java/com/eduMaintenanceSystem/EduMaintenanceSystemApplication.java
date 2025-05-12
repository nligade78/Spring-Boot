package com.eduMaintenanceSystem;

import com.eduMaintenanceSystem.entity.User;

import com.eduMaintenanceSystem.enums.Role;
import com.eduMaintenanceSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class EduMaintenanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduMaintenanceSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findAll().isEmpty()) {
				User mentor = new User("mentor1@example.com", passwordEncoder.encode("password"), Role.MENTOR);
				User student = new User("student1@example.com", passwordEncoder.encode("password"), Role.STUDENT);
				userRepository.saveAll(List.of(mentor, student));
				System.out.println("🟢 Default users created!");
			}
		};
	}
}

