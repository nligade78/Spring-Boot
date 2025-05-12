package com.employeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String gender;
    private int age;
    private String contactNo;
    private String experience;
    private String street;
    private String pincode;

    private String managerName;
    private String departmentName;
}
