package com.employeeManagement.repository;

import com.employeeManagement.entity.Salary;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface SalaryRepository extends ReactiveCrudRepository<Salary, Long> {

    Flux<Salary> findAllByEmployeeId(Long employeeId);

    Flux<Salary> findAllByEmployee_Manager_Id(Long managerId);

    Mono<Salary> findByEmployeeIdAndSalaryMonthAndSalaryYear(Long employeeId, String salaryMonth, int salaryYear);
    Flux<Salary> findAllByEmployeeIdIn(List<Long> employeeIds);

}

