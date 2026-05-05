package com.interview_practice.controller;

import com.interview_practice.model.Student;
import com.interview_practice.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {


    @Autowired
    private StudentServices studentServices;
    public StudentController()
    {
        System.out.println("Student Controller object created at address = "+this.hashCode());
    }

    @GetMapping("/student")
    public int getStudentAddress()
    {
        Student s1= studentServices.getStudent();
        return  s1.hashCode();
    }


}
