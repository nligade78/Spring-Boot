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
@Table("employee")
public class Employee {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String gender;
    private int age;
    private String contactNo;
    private String experience;
    private String street;
    private String pincode;

    private Long managerId;       // Replace relation with foreign key ID
    private Long departmentId;
}
