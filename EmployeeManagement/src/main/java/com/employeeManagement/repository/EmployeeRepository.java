package com.employeeManagement.repository;

import com.employeeManagement.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

    Mono<Employee> findByEmailId(String emailId);
    Flux<Employee> findAllByManagerId(Long managerId);

}
