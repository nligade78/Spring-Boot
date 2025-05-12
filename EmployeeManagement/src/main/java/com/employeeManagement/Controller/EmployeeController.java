package com.employeeManagement.Controller;

import com.employeeManagement.Dto.EmployeeRequest;
import com.employeeManagement.Dto.EmployeeResponse;
import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.Services.EmployeeService;
import com.employeeManagement.entity.Employee;
import com.employeeManagement.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@PreAuthorize("hasRole('MANAGER')")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest request) {
        return new ResponseEntity<>(employeeService.addEmployee(request), HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/view")
    public ResponseEntity<List<EmployeeResponse>> viewEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> request) {
        String email = request.get("emailId");
        String password = request.get("password");

        LoginResponse loginResponse = employeeService.loginEmployee(email, password);

        return ResponseEntity.ok(loginResponse);
    }

}
