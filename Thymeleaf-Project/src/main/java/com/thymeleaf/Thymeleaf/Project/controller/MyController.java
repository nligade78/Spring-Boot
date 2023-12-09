package com.thymeleaf.Thymeleaf.Project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MyController {

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Model model)
    {
        System.out.println("Inside about handler");
        //putting data in model
        model.addAttribute("name","Nikhil Ligade");
        model.addAttribute("currentDate",new Date());
        return "about"; //about.html
    }

    //handling iterations
    @GetMapping("/example-loop")
    public String iterateHandler(Model m)
    {
//        ArrayList<String> data = new ArrayList<String>();
        List<String> names= List.of("Nikhil", "Rahul", "Laxmi", "Vikas");
        m.addAttribute("names", names);

        return"iterate";
    }

    @GetMapping("/condition")
    public String conditionHandler(Model m)
    {
        System.out.println("Condition Handler Executed");

        m.addAttribute("isActive",true);
        m.addAttribute("gender","M");

        List<Integer> list=List.of(233,452,799,245,567,7878);
        m.addAttribute("myList",list);
        return "condition";
    }

}
