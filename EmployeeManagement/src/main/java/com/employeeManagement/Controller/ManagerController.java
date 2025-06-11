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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/managers")
@PreAuthorize("hasRole('ADMIN')")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/add")
    public Mono<ResponseEntity<Manager>> addManager(@RequestBody ManagerRequest request) {
        return managerService.createManager(request)
                .map(manager -> ResponseEntity.status(HttpStatus.CREATED).body(manager))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody Map<String, String> request) {
        String email = request.get("emailId");
        String password = request.get("password");

        return managerService.login(email, password)
                .flatMap(loginResponse -> managerService.getManagerByEmail(email)
                        .map(manager -> {
                            Map<String, Object> success = new HashMap<>();
                            success.put("token", loginResponse.getToken());
                            success.put("id", manager.getId());
                            return ResponseEntity.ok(success);
                        })
                        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of("error", "Manager not found"))))
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid email or password"))))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Internal server error"))));
    }

    @GetMapping("/view")
    public Flux<Manager> viewAllManagers() {
        return managerService.getAllManagers();
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Manager>> updateManager(@PathVariable Long id, @RequestBody ManagerRequest request) {
        return managerService.updateManager(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteManager(@PathVariable Long id) {
        return managerService.deleteManager(id)
                .thenReturn(ResponseEntity.ok("Manager deleted successfully"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Delete failed: " + e.getMessage())));
    }
}
