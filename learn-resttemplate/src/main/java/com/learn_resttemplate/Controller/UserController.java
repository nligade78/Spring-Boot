package com.learn_resttemplate.Controller;

import com.learn_resttemplate.Model.User;
import com.learn_resttemplate.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser()
    {
        try
        {
            List<User> allUser = userService.getAllUser();
            return ResponseEntity.ok(allUser);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

//    @GetMapping("/getUserById/{id}")
//    public User getUserById(@PathVariable int id)
//    {
//      return   userService.getUserById(id);
//    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {

        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam int id)
    {
         try
         {
             User newUSer=userService.getUserById(id);
             return ResponseEntity.ok(newUSer);
         }
         catch (RuntimeException e)
         {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body(e.getMessage());
         }
    }
}
