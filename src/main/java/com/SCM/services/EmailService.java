package com.SCM.services;

public interface EmailService {
    
     public void sendEmail(String to,String subject, String body);

     public void sendEmailWithHtml();

     public void sendEmailWithAttachMents();
}
