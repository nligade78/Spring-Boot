package com.springvalidation.springvalidation.controller;

import com.springvalidation.springvalidation.entity.LoginData;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

    @GetMapping("/form")
    public String openForm(Model m)
    {
        System.out.println("Opening Form");
        m.addAttribute("loginData",new LoginData());
        return "form";
    }
    //handler for processing Form
    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult result)
    {
        if(result.hasErrors()){
            System.out.println(result);
            return "form";
        }
        System.out.println(loginData);
        return "success";
    }
}
