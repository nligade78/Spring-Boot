package com.empManagement.Controller;

import com.empManagement.DTO.EmployeeDTO;
import com.empManagement.Entity.Employee;
import com.empManagement.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    //POST
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee)
    {
       return employeeService.saveEmployee(employee);
    }

    //GET ALL
    @GetMapping
    public List<EmployeeDTO> getAllEmployee()
    {
       return employeeService.getAllEmployee();
    }

    //GET BY ID
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeByID(@PathVariable Long id)
    {
       return employeeService.getEmployeeByID(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee)
    {
        return employeeService.updateEmployee(id,employee);
    }

    //DELETE

    public String deleteEmployee(@PathVariable Long id)
    {
        return employeeService.deleteEmployee(id);
    }
}
