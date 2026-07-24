package com.web.springmvcboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

   @GetMapping("/getEmployee")
    public String getEmployee()
    {
        return "home";
    }


}
