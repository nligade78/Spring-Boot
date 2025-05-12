package com.employeeManagement.Controller;

import com.employeeManagement.Dto.SalaryRequest;
import com.employeeManagement.Services.SalaryService;
import com.employeeManagement.entity.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    // Manager: Add or Update Salary
    @PostMapping("/add")
    public ResponseEntity<?> addSalary(@RequestBody SalaryRequest request) {
        try {
            Salary saved = salaryService.addSalary(request);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Manager: View Salaries of Employees under them
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Salary>> getSalariesByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(salaryService.getSalariesByManager(managerId));
    }

    // Employee: View Own Salary
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Salary>> getSalaryByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(salaryService.getSalaryByEmployee(employeeId));
    }

}

