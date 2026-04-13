package com.SpringDataJPA;

import com.SpringDataJPA.Entity.User;
import com.SpringDataJPA.Repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.Optional;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
	ApplicationContext context=	SpringApplication.run(SpringDataJpaApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		//CreateObjects
		User user1=new User();
		user1.setName("Nikhil****");
		user1.setCity("Pune");
		user1.setStatus("SAC");

		User user2=new User();
		user2.setName("LIGADE");
		user2.setCity("Mumbai");
		user2.setStatus("MBA");


		// ** Saving Single User Object
//		User saveUSer=  userRepository.save(user1);
//		System.out.println(saveUSer+"User Save");

		// ** Saving Multiple User
//		List<User> users = List.of(user1,user2);
//		List<User> saveAllUser= userRepository.saveAll(users);
//		System.out.println("Save All User = "+saveAllUser);

		// ** Get All Users
//		List<User> users=userRepository.findAll();
//		System.out.println("All User"+users); // it's return all users

//		users.forEach(System.out::println); // use foreach in an optimized version
//		for(User user : users)
//		{
//			System.out.println(user);
//		}

		// ** get user by id

		Optional<User> user=userRepository.findById(500);
//		System.out.println("User find by id = "+user); //  if user not found, it will return Optional.empty

//		// checking user present or not
//		if(user.isPresent())
//		{
//			User foundUser=user.get();
//			System.out.println("User Found : "+foundUser);
//		}
//		else
//		{
//			System.out.println("User Not Found");
//		}
//---------------------
//		user.ifPresent(user -> System.out.println("User Found : " + user));
		//---------------
//		user.ifPresentOrElse(user -> System.out.println("User Found : "+user),
//				()-> System.out.println("User not Found"));

		//above three different way to check user is present o not

        // ** Update User

//		Optional<User>updateUser=userRepository.findById(100);

//		if(updateUser.isPresent())
//		{
//			User user = updateUser.get();
//			user.setName("FFF");
//			user.setCity("DELHI");
//			user.setStatus("SINGLE");
//			userRepository.save(user);
//			System.out.println("User Updated !");
//		}


		// ** Delete User

		userRepository.deleteById(52);
		System.out.println("User Deleted !");




	}

}
