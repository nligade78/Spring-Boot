package com.employeeManagement.Services;

import com.employeeManagement.Dto.EmployeeRequest;
import com.employeeManagement.Dto.EmployeeResponse;
import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.entity.Employee;
import com.employeeManagement.entity.Manager;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.repository.ManagerRepository;
import com.employeeManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Manager manager = managerRepository.findById(request.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Employee emp = new Employee();
        emp.setFirstName(request.getFirstName());
        emp.setLastName(request.getLastName());
        emp.setEmailId(request.getEmailId());
//        emp.setPassword(request.getPassword());
        emp.setGender(request.getGender());
        emp.setPassword(passwordEncoder.encode(request.getPassword())); // ✅ ENCODE IT

        emp.setAge(request.getAge());
        emp.setContactNo(request.getContactNo());
        emp.setExperience(request.getExperience());
        emp.setStreet(request.getStreet());
        emp.setPincode(request.getPincode());
        emp.setManager(manager);
        emp.setDepartment(manager.getDepartment()); // ✅ Auto-assign department from manager

        Employee savedEmp = employeeRepository.save(emp);
        return convertToResponse(savedEmp);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Manager manager = managerRepository.findById(request.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        emp.setFirstName(request.getFirstName());
        emp.setLastName(request.getLastName());
        emp.setEmailId(request.getEmailId());
        emp.setPassword(request.getPassword());
        emp.setGender(request.getGender());
        emp.setAge(request.getAge());
        emp.setContactNo(request.getContactNo());
        emp.setExperience(request.getExperience());
        emp.setStreet(request.getStreet());
        emp.setPincode(request.getPincode());
        emp.setManager(manager);
        emp.setDepartment(manager.getDepartment()); // ✅ Auto-assign department from manager

        return convertToResponse(employeeRepository.save(emp));
    }

    public String deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return "Employee deleted successfully";
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToResponse)
                .toList();
    }



    public LoginResponse loginEmployee(String emailId, String password) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmailId(emailId);

        if (employeeOpt.isEmpty()) {
            return new LoginResponse(null, "Employee with this email not found", null);
        }

        Employee employee = employeeOpt.get();

        if (!passwordEncoder.matches(password, employee.getPassword())) {
            return new LoginResponse(null, "Invalid email or password", null);
        }

        String token = jwtUtil.generateToken(emailId, "EMPLOYEE");

        return new LoginResponse(token, "Login Successful", employee.getId());
    }


    private EmployeeResponse convertToResponse(Employee emp) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(emp.getId());
        response.setFirstName(emp.getFirstName());
        response.setLastName(emp.getLastName());
        response.setEmailId(emp.getEmailId());
        response.setGender(emp.getGender());
        response.setAge(emp.getAge());
        response.setContactNo(emp.getContactNo());
        response.setExperience(emp.getExperience());
        response.setStreet(emp.getStreet());
        response.setPincode(emp.getPincode());

        if (emp.getManager() != null) {
            response.setManagerName(emp.getManager().getFirstName() + " " + emp.getManager().getLastName());
        }

        if (emp.getDepartment() != null) {
            response.setDepartmentName(emp.getDepartment().getDept());
        }

        return response;
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }



}
