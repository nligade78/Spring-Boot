package com.JPA.SpringJPA;

import com.JPA.SpringJPA.entity.User;
import com.JPA.SpringJPA.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
	ApplicationContext context = SpringApplication.run(SpringDataJpaApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		/*
		//create users objects
		User user1=new User();
		user1.setName("Nikhil");
		user1.setCity("Pune");
		user1.setStatus("Analyst");

		User user2=new User();
		user2.setName("Rahul");
		user2.setCity("Mumbai");
		user2.setStatus("Analyst");



		//saving single user objects
		//User saveUser=userRepository.save(user1);

		//save multiple users objects
		List<User> users=List.of(user1,user2);
		System.out.println(users);
		Iterable<User>  result= userRepository.saveAll(users);
		result.forEach(System.out::println);
*/

		//update user by id
//		Optional<User> optional= userRepository.findById(1);
//		User user= optional.get();
//		user.setName("Nikhil Ligade");
//		userRepository.save(user);

		//how to get data
//		Optional<User> byId = userRepository.findById(1);
//		System.out.println(byId);

		//get all data
//		Iterable<User> allData= userRepository.findAll();
//		allData.forEach(System.out::println);
//		System.out.println("Crud Operation");

		//Delete the user
//		userRepository.deleteById(53);
//		System.out.println("Deleted");

//        List<User> result= userRepository.findByName("Nikhil");
//        result.forEach(data->{
//            System.out.println(data);
//        });
//
//        List<User> result1= userRepository.findByCity("Pune");
//        for (User data : result1) {
//            System.out.println(data);
//        }

        //getData using Jpql
//        List<User> allUser= userRepository.getAllUser();
//        allUser.forEach(e->{
//            System.out.println(e);
//        });

        //get data using Jpql by name

//        List<User> getName= userRepository.getUserByName("Nikhil","Pune");
//        getName.forEach(e->{
//            System.out.println(e);
//        });

        List<User> users= userRepository.getUsers();
        users.forEach(e->{
            System.out.println(e);
        });
    }

}
