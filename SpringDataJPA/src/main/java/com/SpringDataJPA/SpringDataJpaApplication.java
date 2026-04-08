package com.SpringDataJPA;

import com.SpringDataJPA.Entity.User;
import com.SpringDataJPA.Repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
	ApplicationContext context=	SpringApplication.run(SpringDataJpaApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		//CreateObjects
		User user1=new User();
		user1.setName("Nikhil");
		user1.setCity("Pune");
		user1.setStatus("SAC");

		//Saving Single User Object
		User saveUSer=  userRepository.save(user1);
		System.out.println(saveUSer+"User Save");





	}

}
