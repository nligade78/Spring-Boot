package com.employeeManagement.repository;

import com.employeeManagement.entity.Admin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdminRepository extends ReactiveCrudRepository<Admin, Integer> {

   Mono<Admin> findByEmailAndPassword(String email, String password);

   Mono<Admin> findByEmail(String email);
}
