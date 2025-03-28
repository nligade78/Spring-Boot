package com.smart.controller;

import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    //home handler
    @RequestMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("title", "Home-Smart Contact Manager");
        return "home";

    }

    //about handler
    @RequestMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("title", "About-Smart Contact Manager");
        return "about";
    }

    //signup handler
    @RequestMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("title", "Register-Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    //signing handler
    @GetMapping("/signin")
    public String customLogin(Model model)
    {
        model.addAttribute("title","Login Page");
        return "login";
    }

    @RequestMapping(value = "/do_register",method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user , BindingResult result1,
                               @RequestParam(value = "agreement",defaultValue="false")boolean agreement, Model model, HttpSession session)
    {
        try {
            if (!agreement) {
                System.out.println("You Have not agreed terms and conditions");
                throw new Exception("You have not agreed terms and conditions");

            }

            if (result1.hasErrors()) {
                System.out.println("ERROR " + result1.toString());
                model.addAttribute("user", user);
                return "signup";
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            System.out.println("Agreement" + agreement);
            System.out.println("User" + user);

            User userResult = this.userRepository.save(user);

            model.addAttribute("user", new User());

            session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
            return "signup";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("user", user);
             session.setAttribute("message",new Message("Something Went Wrong !!"+e.getMessage(),"alert-danger"));
            return "signup";
        }
    }

}
