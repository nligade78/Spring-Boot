package com.JPA.SpringJPA.repository;

import com.JPA.SpringJPA.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {

    public List<User> findByName(String name);
    public List<User> findByCity(String city);

    //Jpql query
    @Query("select u FROM User u")
    public List<User> getAllUser();

    @Query("select u From User u WHERE u.name=:n and u.city=:c")
    public List<User> getUserByName(@Param("n") String name,@Param("c") String city); //n Parameter bounded


    //Native Query Methods
    @Query(value = "select * from user",nativeQuery = true)
    public List<User> getUsers();


}
