package com.employeeManagement.repository;

import com.employeeManagement.entity.Admin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdminRepository extends ReactiveCrudRepository<Admin, Long> {
   Mono<Admin> findByEmail(String email);
}
