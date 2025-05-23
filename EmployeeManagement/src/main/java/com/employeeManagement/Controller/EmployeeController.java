package com.employeeManagement.Controller;

import com.employeeManagement.Dto.EmployeeRequest;
import com.employeeManagement.Dto.EmployeeResponse;
import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@PreAuthorize("hasRole('MANAGER')")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public Mono<ResponseEntity<EmployeeResponse>> addEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.addEmployee(request)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                .onErrorResume(e -> {
                    // log.error("Error while adding employee", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<EmployeeResponse>> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    // log.error("Error while updating employee", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id)
                .thenReturn(ResponseEntity.ok("Employee deleted successfully"))
                .onErrorResume(e -> {
                    // log.error("Error deleting employee", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Delete failed"));
                });
    }

    @GetMapping("/view")
    public Flux<EmployeeResponse> viewEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody Map<String, String> request) {
        String email = request.get("emailId");
        String password = request.get("password");

        return employeeService.loginEmployee(email, password)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
                .onErrorResume(e -> {
                    // log.error("Employee login failed", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}
