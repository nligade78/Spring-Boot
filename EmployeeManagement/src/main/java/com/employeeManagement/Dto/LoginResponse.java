package com.employeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String message;
    private Long employeeId;
    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }


}