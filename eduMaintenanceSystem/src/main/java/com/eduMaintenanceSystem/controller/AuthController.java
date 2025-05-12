package com.eduMaintenanceSystem.controller;

import com.eduMaintenanceSystem.dto.LoginRequest;
import com.eduMaintenanceSystem.dto.LoginResponse;
import com.eduMaintenanceSystem.security.JwtUtil;
import com.eduMaintenanceSystem.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user using email and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Set authentication in context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Load full UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(token, "Login successful"));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(new LoginResponse(null, "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new LoginResponse(null, "Authentication failed"));
        }
    }
}
