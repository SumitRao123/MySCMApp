package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.SCM.entities.User;
import com.SCM.forms.UserForm;
import com.SCM.helpers.Message;
import com.SCM.helpers.MessageType;
import com.SCM.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;







@Controller
public class PageController {
    
    @Autowired
     UserService userService;
    
     @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String requestMethodName() {
        return "home";
    }
    
    @RequestMapping("/about")
    public String  getAbout(Model model){
        model.addAttribute("isLogin",true);
        System.out.println("about page loading");
        return "about";
    }

    @RequestMapping("/service")
    public String getService(){
          System.out.println("Service page loading");
          return "service";
    }

    @RequestMapping("/login")
    public String loginMethod() {
        return "login";
    }

    @RequestMapping("/contact")
    public String contactMethod(){
         return "contact";
    }
    
    @RequestMapping("/register")
    public String registerMethod(Model model){
          
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
         return "register";
    }
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String doRegister(@Valid@ModelAttribute UserForm userForm,BindingResult rBindingResult ,HttpSession session ) {
        
        System.out.println(userForm);

        if(rBindingResult.hasErrors()){
             return  "register";
        }
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneno());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("call.png"); 

        User savedUser =  userService.saveUser(user);
        
        Message message =  Message.builder().content("Registration Successfull").type(MessageType.green).build();

        session.setAttribute("message", message);
        if(savedUser != null)
        System.out.println("user saved");

        return  "redirect:/register";
    }
    
}
