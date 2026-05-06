package com.empManagement.Service;

import com.empManagement.DTO.EmployeeDTO;
import com.empManagement.Entity.Employee;
import com.empManagement.Repository.DepartmentRepository;
import com.empManagement.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    //CREATE
    public Employee saveEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    //GET ALL
    public List<EmployeeDTO> getAllEmployee()
    {
        List<Employee> allEmployee = employeeRepository.findAll();

     return   allEmployee.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //GET BY ID
    public EmployeeDTO getEmployeeByID(Long id)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));

        return convertToDTO(employee);
    }

    //UPDATE
    public Employee updateEmployee(Long id, Employee updateEmployee)
    {
        Employee employee =  employeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Employee Not Found"));

        employee.setName(updateEmployee.getName());
        employee.setEmail(updateEmployee.getEmail());
        employee.setDepartment(updateEmployee.getDepartment());

        return employeeRepository.save(employee);
    }

    //DELETE
    public String deleteEmployee(Long id)
    {
        employeeRepository.deleteById(id);
        return "Employee Deleted Successfully";
    }

    //DTO Conversion
    private EmployeeDTO convertToDTO(Employee employee)
    {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        if(employee.getDepartment() != null)
        {
            dto.setDepartmentName(
                    employee.getDepartment().getDepartmentName()
            );
        }
        return dto;
    }

}
