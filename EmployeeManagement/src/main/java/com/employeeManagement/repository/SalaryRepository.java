package com.employeeManagement.repository;

import com.employeeManagement.entity.Salary;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface SalaryRepository extends ReactiveCrudRepository<Salary, Long> {

    // ✅ Get all salaries by a specific employee ID
    Flux<Salary> findAllByEmployeeId(Long employeeId);

    // ✅ Get salary by employeeId + month + year (used for upsert logic)
    Mono<Salary> findByEmployeeIdAndSalaryMonthAndSalaryYear(Long employeeId, String salaryMonth, int salaryYear);

    // ✅ Get all salaries by list of employee IDs (used for manager salary view)
    Flux<Salary> findAllByEmployeeIdIn(List<Long> employeeIds);

    // ❌ Removed: findAllByEmployee_Manager_Id(Long managerId); — this was invalid
}
