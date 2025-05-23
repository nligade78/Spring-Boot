package com.employeeManagement.Controller;

import com.employeeManagement.Dto.SalaryRequest;
import com.employeeManagement.Dto.SalaryResponse;
import com.employeeManagement.Services.SalaryService;
import com.employeeManagement.entity.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    // Manager: Add or Update Salary
    @PostMapping("/add")
    public Mono<ResponseEntity<SalaryResponse>> addSalary(@RequestBody SalaryRequest request) {
        return salaryService.addSalary(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(ex -> {
                    SalaryResponse errorResponse = new SalaryResponse();
//                    errorResponse.setMessage(ex.getMessage()); // assuming there's a setMessage method
                    return Mono.just(ResponseEntity.badRequest().body(errorResponse));
                });
    }





    // Manager: View Salaries of Employees under them
    @GetMapping("/manager/{managerId}")
    public Flux<Salary> getSalariesByManager(@PathVariable Long managerId) {
        return salaryService.getSalariesByManager(managerId);
    }

    // Employee: View Own Salary
    @GetMapping("/employee/{employeeId}")
    public Flux<Salary> getSalaryByEmployee(@PathVariable Long employeeId) {
        return salaryService.getSalaryByEmployee(employeeId);
    }
}
