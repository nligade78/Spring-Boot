package com.employeeManagement.repository;

import com.employeeManagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findAllByEmployeeId(Long employeeId);
    List<Salary> findAllByEmployee_Manager_Id(Long managerId);
    Optional<Salary> findByEmployeeIdAndSalaryMonthAndSalaryYear(Long employeeId, String salaryMonth, int salaryYear);

}

