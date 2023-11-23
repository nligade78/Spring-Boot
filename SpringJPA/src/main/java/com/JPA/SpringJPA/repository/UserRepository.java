package com.JPA.SpringJPA.repository;

import com.JPA.SpringJPA.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

}
