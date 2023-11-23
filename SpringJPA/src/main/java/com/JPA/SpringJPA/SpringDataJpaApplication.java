package com.JPA.SpringJPA;

import com.JPA.SpringJPA.entity.User;
import com.JPA.SpringJPA.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
	ApplicationContext context = SpringApplication.run(SpringDataJpaApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		User user=new User();
		user.setName("Nikhil");
		user.setCity("Pune");
		user.setStatus("Analyst");

		User saveUser=userRepository.save(user);
		System.out.println(saveUser);

		System.out.println("Crud Operation");
	}

}
