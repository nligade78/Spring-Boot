package com.smart.controller;

import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    //home handler
    @RequestMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("title", "Smart Contact Manager");
        return "home";
    }

    //about handler
    @RequestMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("title", "About-Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("title", "Register-Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    //this handler for register user
    @RequestMapping(value = "/do_register",method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user ,BindingResult result, @RequestParam(value = "agreement",defaultValue="false")boolean agreement, Model model, HttpSession session)
    {
        try {
            if(!agreement)
            {
                System.out.println("You Have not agreed terms and conditions");
                throw new Exception("You have not agreed terms and conditions");

            }
            if(result.hasErrors())
            {
                System.out.println("Error"+result.toString());
                model.addAttribute("user",user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");


            System.out.println("Agreement"+agreement);
            System.out.println("User"+user);
            User userResult= this.userRepository.save(user);

            model.addAttribute("user", new User());

            session.setAttribute("message",new Message("Successfully Register !!","alert-success"));
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
