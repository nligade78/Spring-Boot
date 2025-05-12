package com.employeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryRequest {
    private Double basicPay;
    private Double hra;
    private Double bonus;
    private String salaryMonth;
    private int salaryYear;
    private Long employeeId;
}

