package com.employeeManagement.Services;

import com.employeeManagement.Dto.SalaryRequest;
import com.employeeManagement.entity.Employee;
import com.employeeManagement.entity.Salary;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


//    public Salary addOrUpdateSalary(SalaryRequest request) {
//        Employee employee = employeeRepository.findById(request.getEmployeeId())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        // Find salary by employeeId, month, and year
//        Optional<Salary> existingSalary = salaryRepository
//                .findByEmployeeIdAndSalaryMonthAndSalaryYear(
//                        request.getEmployeeId(), request.getSalaryMonth(), request.getSalaryYear());
//
//        Salary salary = existingSalary.orElse(new Salary());
//
//        salary.setBasicPay(request.getBasicPay());
//        salary.setHra(request.getHra());
//        salary.setBonus(request.getBonus());
//        salary.setSalaryMonth(request.getSalaryMonth());
//        salary.setSalaryYear(request.getSalaryYear());
//        salary.setEmployee(employee);
//
//        return salaryRepository.save(salary);
//    }

//    public Salary addSalary(SalaryRequest request) {
//        Employee employee = employeeRepository.findById(request.getEmployeeId())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        // Check if salary already exists for this employee, month, and year
//        Optional<Salary> existingSalary = salaryRepository
//                .findByEmployeeIdAndSalaryMonthAndSalaryYear(
//                        request.getEmployeeId(), request.getSalaryMonth(), request.getSalaryYear());
//
//        if (existingSalary.isPresent()) {
//            throw new RuntimeException("Salary already exists for this employee in the specified month and year");
//        }
//
//        // Add new salary
//        Salary salary = new Salary();
//        salary.setBasicPay(request.getBasicPay());
//        salary.setHra(request.getHra());
//        salary.setBonus(request.getBonus());
//        salary.setSalaryMonth(request.getSalaryMonth());
//        salary.setSalaryYear(request.getSalaryYear());
//        salary.setEmployee(employee);
//
//        return salaryRepository.save(salary);
//    }

    public Salary addSalary(SalaryRequest request) {
        // Find the employee by ID
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Check if a salary already exists for this employee, month, and year
        Optional<Salary> existingSalary = salaryRepository
                .findByEmployeeIdAndSalaryMonthAndSalaryYear(
                        request.getEmployeeId(), request.getSalaryMonth(), request.getSalaryYear());

        if (existingSalary.isPresent()) {
            // If a salary already exists, update it
            Salary salary = existingSalary.get();
            salary.setBasicPay(request.getBasicPay());
            salary.setHra(request.getHra());
            salary.setBonus(request.getBonus());
            return salaryRepository.save(salary);  // Save the updated salary
        }

        // If no salary exists for this employee, month, and year, create a new salary
        Salary salary = new Salary();
        salary.setBasicPay(request.getBasicPay());
        salary.setHra(request.getHra());
        salary.setBonus(request.getBonus());
        salary.setSalaryMonth(request.getSalaryMonth());
        salary.setSalaryYear(request.getSalaryYear());
        salary.setEmployee(employee);

        return salaryRepository.save(salary);  // Save the new salary
    }
    public List<Salary> getSalariesByManager(Long managerId) {
        return salaryRepository.findAllByEmployee_Manager_Id(managerId);
    }

    public List<Salary> getSalaryByEmployee(Long employeeId) {
        return salaryRepository.findAllByEmployeeId(employeeId);
    }


}

