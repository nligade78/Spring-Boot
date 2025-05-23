package com.employeeManagement.Controller;

import com.employeeManagement.Services.AdminServices;
import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @PostMapping("/addAdmin")
    public Mono<ResponseEntity<Admin>> createAdmin(@RequestBody Admin admin) {
        return adminServices.createAdmin(admin)
                .map(savedAdmin -> ResponseEntity.ok(savedAdmin))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Admin>build()));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        return adminServices.login(email, password)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
