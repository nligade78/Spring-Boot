package com.empManagement.Controller;

import com.empManagement.DTO.EmployeeDTO;
import com.empManagement.Entity.Employee;
import com.empManagement.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    //POST
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee)
    {
        Employee emp=null;
        try
        {
            emp=this.employeeService.saveEmployee(employee);
            System.out.println(emp);
            return ResponseEntity.of(Optional.of(emp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee()
    {
        List<EmployeeDTO> empList=employeeService.getAllEmployee();
        if(empList.size()<=0)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(empList);
    }


    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable Long id)
    {
        EmployeeDTO employee= employeeService.getEmployeeByID(id);
        if(employee == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(employee));
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee)
    {
//        return employeeService.updateEmployee(id,employee);
       Employee employeeUpdate= employeeService.updateEmployee(id,employee);
       if(employeeUpdate == null)
       {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.of(Optional.of(employeeUpdate));
    }

    //DELETE

    public ResponseEntity<String> deleteEmployee(@PathVariable Long id)
    {
//        return employeeService.deleteEmployee(id);
        String deleteEmployee = employeeService.deleteEmployee(id);
        if(deleteEmployee == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
