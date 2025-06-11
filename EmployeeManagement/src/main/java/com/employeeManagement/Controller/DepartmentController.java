package com.employeeManagement.Controller;

import com.employeeManagement.Services.DepartmentService;
import com.employeeManagement.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public Mono<ResponseEntity<Department>> addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department)
                .map(savedDept -> ResponseEntity.status(HttpStatus.CREATED).body(savedDept))
                .onErrorResume(e -> {
                    // log.error("Error adding department", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @GetMapping("/view")
    public Flux<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Department>> updateDepartment(@PathVariable Long id,
                                                             @RequestBody Department updatedDept) {
        return departmentService.updateDepartment(id, updatedDept)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    // log.error("Error updating department", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id)
                .thenReturn(ResponseEntity.ok("Department deleted successfully"))
                .onErrorResume(e -> {
                    // log.error("Error deleting department", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Delete failed"));
                });
    }
}
