package com.SCM.controller;

import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;





@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/dashboard")
    public String userDashBoard() {
         return  "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfile(Authentication authentication) {
 

        return "user/profile";
    }
    
    
}
