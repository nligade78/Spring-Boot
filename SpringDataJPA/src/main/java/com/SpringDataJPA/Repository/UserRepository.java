package com.SpringDataJPA.Repository;

import com.SpringDataJPA.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
