package com.empManagement.DTO;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private String email;
    private String departmentName;
}
