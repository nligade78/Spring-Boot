package com.employeeManagement.Controller;

import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.Dto.ManagerRequest;
import com.employeeManagement.Services.ManagerService;
import com.employeeManagement.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/managers")
@PreAuthorize("hasRole('ADMIN')")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/add")
    public ResponseEntity<Manager> addManager(@RequestBody ManagerRequest request) {
        return new ResponseEntity<>(managerService.createManager(request), HttpStatus.CREATED);
    }

    // Corrected: Removed the broken commented login method

    @PostMapping("/login")
    @PreAuthorize("permitAll()") // Allow public access to login
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("emailId");
        String password = request.get("password");

        LoginResponse loginResponse = managerService.login(email, password);

        // Also get the manager entity to retrieve the ID
        Manager manager = managerService.getManagerByEmail(email);

        return ResponseEntity.ok(Map.of(
                "token", loginResponse.getToken(),
                "id", manager.getId()
        ));
    }

    @GetMapping("/view")
    public ResponseEntity<List<Manager>> viewAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Long id, @RequestBody ManagerRequest request) {
        return ResponseEntity.ok(managerService.updateManager(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.deleteManager(id));
    }
}
