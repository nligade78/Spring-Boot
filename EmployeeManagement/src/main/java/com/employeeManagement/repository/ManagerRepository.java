package com.employeeManagement.repository;

import com.employeeManagement.entity.Manager;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ManagerRepository extends ReactiveCrudRepository<Manager, Long> {
    Mono<Manager> findByEmailId(String emailId);
}
