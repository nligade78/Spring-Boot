package com.employeeManagement.Services;

import com.employeeManagement.Dto.SalaryRequest;
import com.employeeManagement.Dto.SalaryResponse;
import com.employeeManagement.entity.Salary;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.repository.SalaryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryService(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Add or update salary reactively for an employee for a specific month and year.
     */
    public Mono<SalaryResponse> addSalary(SalaryRequest request) {
        return employeeRepository.findById(request.getEmployeeId())
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found")))
                .flatMap(employee ->
                        salaryRepository.findByEmployeeIdAndSalaryMonthAndSalaryYear(
                                        request.getEmployeeId(), request.getSalaryMonth(), request.getSalaryYear())
                                .flatMap(existingSalary -> {
                                    existingSalary.setBasicPay(request.getBasicPay());
                                    existingSalary.setHra(request.getHra());
                                    existingSalary.setBonus(request.getBonus());
                                    return salaryRepository.save(existingSalary);
                                })
                                .switchIfEmpty(Mono.defer(() -> {
                                    Salary salary = new Salary();
                                    salary.setBasicPay(request.getBasicPay());
                                    salary.setHra(request.getHra());
                                    salary.setBonus(request.getBonus());
                                    salary.setSalaryMonth(request.getSalaryMonth());
                                    salary.setSalaryYear(request.getSalaryYear());
                                    salary.setEmployeeId(employee.getId());
                                    return salaryRepository.save(salary);
                                }))
                )
                .map(saved -> new SalaryResponse(
                        saved.getEmployeeId(),
                        saved.getSalaryMonth(),
                        saved.getSalaryYear(),
                        saved.getBasicPay(),
                        saved.getHra(),
                        saved.getBonus()
                ));
    }

    /**
     * Fetch all salaries for employees managed by a specific manager reactively.
     * Optimized to fetch all employee IDs and then salaries in bulk.
     */
    public Flux<Salary> getSalariesByManager(Long managerId) {
        return employeeRepository.findAllByManagerId(managerId)
                .map(employee -> employee.getId())
                .collectList()
                .flatMapMany(employeeIds -> {
                    if (employeeIds.isEmpty()) {
                        return Flux.empty();
                    }
                    return salaryRepository.findAllByEmployeeIdIn(employeeIds);
                });
    }

    /**
     * Fetch all salary records for a specific employee reactively.
     */
    public Flux<Salary> getSalaryByEmployee(Long employeeId) {
        return salaryRepository.findAllByEmployeeId(employeeId);
    }
}
