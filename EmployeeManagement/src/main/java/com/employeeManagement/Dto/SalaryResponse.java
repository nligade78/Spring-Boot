package com.employeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryResponse {
    private Long employeeId;
    private String salaryMonth;
    private int salaryYear;
    private Double basicPay;
    private Double hra;
    private Double bonus;
}