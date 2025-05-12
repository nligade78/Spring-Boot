package com.employeeManagement.Controller;

import com.employeeManagement.Services.AdminServices;
import com.employeeManagement.Dto.LoginRequest;
import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            Admin saved = adminServices.createAdmin(admin);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        LoginResponse response = adminServices.login(email, password);
        return ResponseEntity.ok(response);
    }

}
