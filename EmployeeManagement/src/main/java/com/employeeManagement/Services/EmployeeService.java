package com.employeeManagement.Services;

import com.employeeManagement.Dto.EmployeeRequest;
import com.employeeManagement.Dto.EmployeeResponse;
import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.entity.Department;
import com.employeeManagement.entity.Employee;
import com.employeeManagement.entity.Manager;
import com.employeeManagement.repository.DepartmentRepository;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.repository.ManagerRepository;
import com.employeeManagement.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public EmployeeService(EmployeeRepository employeeRepository,
                           ManagerRepository managerRepository,
                           DepartmentRepository departmentRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Mono<EmployeeResponse> addEmployee(EmployeeRequest request) {
        return managerRepository.findById(request.getManagerId())
                .switchIfEmpty(Mono.error(new RuntimeException("Manager not found")))
                .flatMap(manager -> {
                    Employee emp = new Employee();
                    emp.setFirstName(request.getFirstName());
                    emp.setLastName(request.getLastName());
                    emp.setEmailId(request.getEmailId());
                    emp.setPassword(passwordEncoder.encode(request.getPassword()));
                    emp.setGender(request.getGender());
                    emp.setAge(request.getAge());
                    emp.setContactNo(request.getContactNo());
                    emp.setExperience(request.getExperience());
                    emp.setStreet(request.getStreet());
                    emp.setPincode(request.getPincode());
                    emp.setManagerId(manager.getId());
                    emp.setDepartmentId(manager.getDepartmentId());
                    return employeeRepository.save(emp);
                })
                .flatMap(this::enrichEmployeeResponse);
    }

    public Mono<EmployeeResponse> updateEmployee(Long id, EmployeeRequest request) {
        Mono<Employee> empMono = employeeRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found")));

        Mono<Manager> managerMono = managerRepository.findById(request.getManagerId())
                .switchIfEmpty(Mono.error(new RuntimeException("Manager not found")));

        return Mono.zip(empMono, managerMono)
                .flatMap(tuple -> {
                    Employee emp = tuple.getT1();
                    Manager manager = tuple.getT2();

                    emp.setFirstName(request.getFirstName());
                    emp.setLastName(request.getLastName());
                    emp.setEmailId(request.getEmailId());

                    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                        emp.setPassword(passwordEncoder.encode(request.getPassword()));
                    }

                    emp.setGender(request.getGender());
                    emp.setAge(request.getAge());
                    emp.setContactNo(request.getContactNo());
                    emp.setExperience(request.getExperience());
                    emp.setStreet(request.getStreet());
                    emp.setPincode(request.getPincode());

                    emp.setManagerId(manager.getId());
                    emp.setDepartmentId(manager.getDepartmentId());

                    return employeeRepository.save(emp);
                })
                .flatMap(this::enrichEmployeeResponse);
    }

    public Mono<Void> deleteEmployee(Long id) {
        return employeeRepository.deleteById(id);
    }

    public Flux<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .flatMap(this::enrichEmployeeResponse);
    }

    public Mono<LoginResponse> loginEmployee(String emailId, String password) {
        return employeeRepository.findByEmailId(emailId)
                .flatMap(employee -> {
                    if (!passwordEncoder.matches(password, employee.getPassword())) {
                        return Mono.just(new LoginResponse(null, "Invalid email or password", null));
                    }
                    String token = jwtUtil.generateToken(emailId, "EMPLOYEE");
                    return Mono.just(new LoginResponse(token, "Login Successful", employee.getId()));
                })
                .switchIfEmpty(Mono.just(new LoginResponse(null, "Employee with this email not found", null)));
    }

    private EmployeeResponse convertToResponse(Employee emp) {
        return new EmployeeResponse(
                emp.getId(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getEmailId(),
                emp.getGender(),
                emp.getAge(),
                emp.getContactNo(),
                emp.getExperience(),
                emp.getStreet(),
                emp.getPincode(),
                null,
                null
        );
    }

    private Mono<EmployeeResponse> enrichEmployeeResponse(Employee emp) {
        EmployeeResponse response = convertToResponse(emp);

        return managerRepository.findById(emp.getManagerId())
                .flatMap(manager -> {
                    if (manager != null) {
                        response.setManagerName(manager.getFirstName() + " " + manager.getLastName());
                        return departmentRepository.findById(manager.getDepartmentId())
                                .map(dept -> {
                                    if (dept != null) {
                                        response.setDepartmentName(dept.getDept());
                                    }
                                    return response;
                                })
                                .defaultIfEmpty(response);
                    } else {
                        return Mono.just(response);
                    }
                })
                .switchIfEmpty(Mono.just(response));
    }

    public Mono<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmailId(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found")));
    }
}
