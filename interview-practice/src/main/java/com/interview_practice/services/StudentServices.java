package com.interview_practice.services;

import com.interview_practice.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServices {

    @Autowired
    private Student student;
    public StudentServices()
    {
        System.out.println("Student service object created at address = "
                +this.hashCode());
    }

    public Student getStudent()
    {

        return student;

    }
}
