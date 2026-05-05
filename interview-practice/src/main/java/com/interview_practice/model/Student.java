package com.interview_practice.model;

import org.springframework.stereotype.Component;

@Component
public class Student {

    public Student()
    {
        System.out.println("Student Model object created at address = "+this.hashCode());
    }
}
