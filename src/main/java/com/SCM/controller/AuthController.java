package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SCM.entities.User;
import com.SCM.helpers.Message;
import com.SCM.helpers.MessageType;
import com.SCM.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AuthController {
      
    @Autowired
    UserService userService;
        
     
      @GetMapping("/verify-email")
      public String verifyToken(@RequestParam("token") String emailToken,HttpSession session) {
          User user =  userService.getEmailToken(emailToken);
          if(user != null){
           
              
              userService.saveVerifiedUser(user);
               session.setAttribute("message", Message.builder()
                        .type(MessageType.green)
                        .content("You email is verified. Now you can login  ")
                        .build());
              return "success_page";
          }

          session.setAttribute("message", Message.builder()
          .type(MessageType.red)
          .content("Email not verified ! Token is not associated with user .")
          .build());
        return  "error_page";
      }
      
}
