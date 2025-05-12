package com.employeeManagement.Dto;

import lombok.Data;

@Data
public class AdminRegisterRequest {
    private String email;
    private String password;
}
