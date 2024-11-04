package com.SCM.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {
   
    public static String getEmailLoggedIn(Authentication authentication){

        // logic to find the email whether it is google and github
        if( authentication instanceof OAuth2AuthenticationToken){
             var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
             var client = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
             var oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
             String username = "";
             if(client.equalsIgnoreCase("Google")){
                        username = oauth2User.getAttribute("email").toString();
             }
             else if(client.equalsIgnoreCase("Github")){
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                : oauth2User.getAttribute("login").toString() + "@gmail.com";
             }
             return username;
        }
        else{
             System.out.println("Normal User login");
             return authentication.getName();
        }
    }

    public static String getEmailVerficationLink(String emailToken){
           String link = "http://localhost:9090/auth/verify-email?token=" + emailToken;

           return link;
    }
}
