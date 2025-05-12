package com.employeeManagement.Services;

import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.entity.Admin;
import com.employeeManagement.repository.AdminRepository;
import com.employeeManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServices {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Admin createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }


    public LoginResponse login(String email, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);

        if (adminOpt.isEmpty()) {
            return new LoginResponse(null, "Admin with this email not found");
        }

        Admin admin = adminOpt.get();

        // Check password using BCrypt
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            return new LoginResponse(null, "Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(email, "ADMIN");

        return new LoginResponse(token, "Login Successful");
    }

}
