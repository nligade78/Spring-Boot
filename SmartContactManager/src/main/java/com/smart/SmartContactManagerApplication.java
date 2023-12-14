package com.smart;

import com.smart.entity.Contact;
import com.smart.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartContactManagerApplication {


	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);


		System.out.println("Working");

		Contact contact = new Contact();
		contact.setCId(23);
		System.out.println(contact);

		User user = new User();









	}

}
