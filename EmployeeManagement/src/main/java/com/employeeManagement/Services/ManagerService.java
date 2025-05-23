package com.employeeManagement.Services;

import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.Dto.ManagerRequest;
import com.employeeManagement.entity.Manager;
import com.employeeManagement.repository.DepartmentRepository;
import com.employeeManagement.repository.ManagerRepository;
import com.employeeManagement.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ManagerService(ManagerRepository managerRepository,
                          DepartmentRepository departmentRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.managerRepository = managerRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Custom NotFoundException for clarity
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }

    // Create a new Manager and encode the password
    public Mono<Manager> createManager(ManagerRequest request) {
        return departmentRepository.findById(request.getDepartmentId())
                .switchIfEmpty(Mono.error(new NotFoundException("Department not found")))
                .flatMap(department -> {
                    Manager manager = new Manager();
                    manager.setFirstName(request.getFirstName());
                    manager.setLastName(request.getLastName());
                    manager.setEmailId(request.getEmailId());
                    manager.setPassword(passwordEncoder.encode(request.getPassword()));
                    manager.setGender(request.getGender());
                    manager.setAge(request.getAge());
                    manager.setContactNo(request.getContactNo());
                    manager.setExperience(request.getExperience());
                    manager.setStreet(request.getStreet());
                    manager.setPincode(request.getPincode());
                    manager.setDepartmentId(department.getId());
                    return managerRepository.save(manager);
                });
    }

    // Login logic for Manager - verify password and generate JWT token
    public Mono<LoginResponse> login(String emailId, String password) {
        return managerRepository.findByEmailId(emailId)
                .flatMap(manager -> {
                    if (!passwordEncoder.matches(password, manager.getPassword())) {
                        return Mono.just(new LoginResponse(null, "Invalid email or password"));
                    }
                    String token = jwtUtil.generateToken(emailId, "MANAGER");
                    return Mono.just(new LoginResponse(token, "Login Successful"));
                })
                .switchIfEmpty(Mono.just(new LoginResponse(null, "Manager with this email not found")));
    }

    // Fetch all managers
    public Flux<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    // Update an existing manager, encode password only if it is not encoded yet
    public Mono<Manager> updateManager(Long id, ManagerRequest request) {
        return managerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Manager not found")))
                .flatMap(manager ->
                        departmentRepository.findById(request.getDepartmentId())
                                .switchIfEmpty(Mono.error(new NotFoundException("Department not found")))
                                .flatMap(dept -> {
                                    manager.setFirstName(request.getFirstName());
                                    manager.setLastName(request.getLastName());
                                    manager.setEmailId(request.getEmailId());
                                    manager.setGender(request.getGender());
                                    manager.setAge(request.getAge());
                                    manager.setContactNo(request.getContactNo());
                                    manager.setExperience(request.getExperience());
                                    manager.setStreet(request.getStreet());
                                    manager.setPincode(request.getPincode());
                                    manager.setDepartmentId(dept.getId());

                                    if (request.getPassword() != null && !request.getPassword().isBlank()) {
                                        // Encode password only if it doesn't look encoded (bcrypt hash check)
                                        if (!request.getPassword().startsWith("$2a$") && !request.getPassword().startsWith("$2b$")) {
                                            manager.setPassword(passwordEncoder.encode(request.getPassword()));
                                        } else {
                                            manager.setPassword(request.getPassword());
                                        }
                                    }
                                    return managerRepository.save(manager);
                                })
                );
    }

    // Delete manager by id
    public Mono<String> deleteManager(Long id) {
        return managerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Manager not found")))
                .flatMap(manager -> managerRepository.delete(manager)
                        .then(Mono.just("Manager deleted successfully"))
                );
    }

    // Get manager by email
    public Mono<Manager> getManagerByEmail(String email) {
        return managerRepository.findByEmailId(email)
                .switchIfEmpty(Mono.error(new NotFoundException("Manager not found")));
    }
}
