package com.empManagement.Controller;

import com.empManagement.Entity.Department;
import com.empManagement.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @PostMapping
    public Department createDepartment(@RequestBody Department department)
    {
        return departmentRepository.save(department);
    }

    @GetMapping
    public List<Department> getAllDepartment()
    {
       return departmentRepository.findAll();
    }
}
