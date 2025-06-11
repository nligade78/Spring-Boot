package com.employeeManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("salary")
public class Salary {

    @Id
    private Long id;

    private Double basicPay;
    private Double hra;
    private Double bonus;
    private String salaryMonth;
    private int salaryYear;

    private Long employeeId;
}
