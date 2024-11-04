package com.SCM.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.entities.Contact;
import com.SCM.services.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contacts/{contactid}")
    public Contact getInfo(@PathVariable String contactid) {
        System.out.println(contactid);
        return contactService.getById(contactid);
    }
    
}
