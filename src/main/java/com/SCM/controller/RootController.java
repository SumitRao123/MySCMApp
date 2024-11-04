package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.SCM.entities.User;
import com.SCM.helpers.Helper;
import com.SCM.services.UserService;


@ControllerAdvice
public class RootController {
    
    @Autowired
    private UserService userService;


    @ModelAttribute  // Now this method will become common to all request around all the user
    public void getLoggedInUser(Model model,Authentication authentication)
    {
        if(authentication == null) return;
         String username = Helper.getEmailLoggedIn(authentication);
         System.out.println("username = " + username);
         User user = userService.getUserByEmail(username);
         System.out.println(user.getName());
         model.addAttribute("loggedInUser", user);

    }
}
