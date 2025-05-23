package com.employeeManagement.Services;

import com.employeeManagement.Exception.DepartmentNotFoundException;
import com.employeeManagement.entity.Department;
import com.employeeManagement.repository.DepartmentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Save department reactively with validation
    public Mono<Department> addDepartment(Department department) {
        if (department.getDept() == null || department.getDept().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Department name cannot be empty"));
        }
        return departmentRepository.save(department)
                .doOnSuccess(d -> logger.info("Department added: {}", d.getDept()));
    }

    // Get all departments reactively
    public Flux<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Update department reactively with validation and custom exception
    public Mono<Department> updateDepartment(Long id, Department updatedDept) {
        if (updatedDept.getDept() == null || updatedDept.getDept().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Department name cannot be empty"));
        }

        return departmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new DepartmentNotFoundException("Department not found with id: " + id)))
                .flatMap(dept -> {
                    dept.setDept(updatedDept.getDept());
                    dept.setDescription(updatedDept.getDescription());
                    return departmentRepository.save(dept);
                })
                .doOnSuccess(d -> logger.info("Department updated: {}", d.getDept()));
    }

    // Delete department reactively
    public Mono<Void> deleteDepartment(Long id) {
        logger.info("Deleting department with id: {}", id);
        return departmentRepository.deleteById(id);
    }
}
