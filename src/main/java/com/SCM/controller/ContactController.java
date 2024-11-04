package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SCM.entities.Contact;
import com.SCM.entities.User;
import com.SCM.forms.ContactForm;
import com.SCM.forms.ContactSearchForm;
import com.SCM.helpers.AppConstants;
import com.SCM.helpers.Helper;
import com.SCM.helpers.Message;
import com.SCM.helpers.MessageType;
import com.SCM.services.ContactService;
import com.SCM.services.ImageService;
import com.SCM.services.UserService;
import com.SCM.validators.ValidFile;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;
    
    
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    public String addContacts(Model model) {
        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession httpSession) {

        if (result.hasErrors()) {

            httpSession.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailLoggedIn(authentication);

        User user = userService.getUserByEmail(username);
        String filename = UUID.randomUUID().toString();
        String url = imageService.getImage(contactForm.getContactImage(), filename);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);
        contact.setFavourite(contactForm.isFavourite());
        contact.setWebSiteLink(contactForm.getWebSiteLink());
        contact.setId(UUID.randomUUID().toString());
        contact.setPicture(url);
        contactService.save(contact);

        httpSession.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());
        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewContact(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Authentication authentication,
            Model model) {
        String username = Helper.getEmailLoggedIn(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        System.out.println(pageContact);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchContact(@ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Authentication authentication,
            Model model) {
         
        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
        User user=  userService.getUserByEmail(Helper.getEmailLoggedIn(authentication));

        Page<Contact> pageContact = null;
        if(contactSearchForm.getField().equals("name")){
             pageContact = contactService.searchByName(user, contactSearchForm.getValue(), page, size, sortBy, direction);
        }
        else if(contactSearchForm.getField().equals("email")){
             pageContact = contactService.searchByEmail(user, contactSearchForm.getValue(), page, size, sortBy, direction);
        }
        else if(contactSearchForm.getField().equals("phone")){
              pageContact = contactService.searchByPhoneNumber(user, contactSearchForm.getValue(), page, size, sortBy, direction);
        }
         
        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }
    
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId,HttpSession session) {
       contactService.delete(contactId);
         
       logger.info("contactId {} deleted", contactId);
       session.setAttribute("message",
       Message.builder()
               .content("Contact is Deleted successfully !! ")
               .type(MessageType.green)
               .build()

       );
        return  "redirect:/user/contacts";
    }

    @GetMapping("/view/{contactId}")
    public String getUpdateView(@PathVariable String contactId, Model model) {
        
        ContactForm contactForm = new ContactForm();
        var contact = contactService.getById(contactId);
        contactForm.setName(contact.getName());
        contactForm.setAddress(contact.getAddress());
        contactForm.setPicture(contact.getPicture());
        contactForm.setDescription(contact.getDescription());
        contactForm.setEmail(contact.getEmail());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setWebSiteLink(contact.getWebSiteLink());
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        
        return "user/update_contact_view";
    }
    
    @RequestMapping(value = "/update/{contactId}", method=RequestMethod.POST)
    public String updateContactView(@PathVariable String contactId, @ModelAttribute ContactForm contactForm,BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavourite(contactForm.isFavourite());
        con.setWebSiteLink(contactForm.getWebSiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }
    
    
}
