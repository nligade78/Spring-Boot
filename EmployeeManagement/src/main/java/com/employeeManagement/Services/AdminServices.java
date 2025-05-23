package com.employeeManagement.Services;

import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.entity.Admin;
import com.employeeManagement.repository.AdminRepository;
import com.employeeManagement.security.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class AdminServices {

    private static final Logger logger = LoggerFactory.getLogger(AdminServices.class);

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminServices(AdminRepository adminRepository,
                        BCryptPasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Reactive createAdmin
    public Mono<Admin> createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin)
                .doOnSuccess(a -> logger.info("Created admin with email: {}", a.getEmail()));
    }

    // Reactive login method returning Mono<LoginResponse>
    public Mono<LoginResponse> login(String email, String password) {
        return Mono.defer(() -> adminRepository.findByEmail(email)
                .flatMap(admin -> {
                    if (passwordEncoder.matches(password, admin.getPassword())) {
                        String token = jwtUtil.generateToken(email, "ADMIN");
                        logger.info("Admin login successful for email: {}", email);
                        return Mono.just(new LoginResponse(token, "Login Successful"));
                    } else {
                        logger.warn("Invalid password attempt for email: {}", email);
                        return Mono.just(new LoginResponse(null, "Invalid email or password"));
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("Admin with email {} not found", email);
                    return Mono.just(new LoginResponse(null, "Admin with this email not found"));
                }))
        );
    }
}
