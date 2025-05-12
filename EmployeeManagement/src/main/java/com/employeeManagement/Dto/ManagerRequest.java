package com.employeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ManagerRequest {
    public String firstName;
    public String lastName;
    public String emailId;
    public String password;
    public String gender;
    public int age;
    public String contactNo;
    public String experience;
    public String street;
    public String pincode;
    public Long departmentId;
}
