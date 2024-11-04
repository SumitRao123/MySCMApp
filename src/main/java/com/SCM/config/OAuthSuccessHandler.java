package com.SCM.config;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.SCM.entities.Providers;
import com.SCM.entities.User;
import com.SCM.helpers.AppConstants;
import com.SCM.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

     Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
     
     @Autowired
     UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
          logger.info("OAuthAuthenticationSuccessHandler");
           
          // getting authentication info
          OAuth2AuthenticationToken  oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
          String   oauthProvider =  oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

          OAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

          User user = new User();
          user.setUserid(UUID.randomUUID().toString());
          user.setEmailVerified(true);
          user.setEnabled(true);
          user.setPassword("dummy");
          user.setRoleList(List.of(AppConstants.ROLE));
          if(oauthProvider.equalsIgnoreCase("Google")){
                  user.setName(oAuth2User.getAttribute("name").toString());
                  user.setEmail(oAuth2User.getAttribute("email").toString());
                  user.setProfilePic(oAuth2User.getAttribute("picture").toString());
                  user.setProvider(Providers.GOOGLE);
                  user.setProviderUserId(oAuth2User.getName());
                  user.setAbout("This is google login ");
          }
          else if(oauthProvider.equalsIgnoreCase("Github")){
            String email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString()
                           : oAuth2User.getAttribute("login").toString() + "@gmail.com";
            String name = oAuth2User.getAttribute("name").toString();
            String profile = oAuth2User.getAttribute("avatar_url").toString();
            String id = oAuth2User.getName();
            user.setEmail(email);
            user.setAbout("This is github login");
            user.setProfilePic(profile);
            user.setName(name);
            user.setProvider(Providers.GITHUB);
            user.setProviderUserId(id);

          }
          User savedUser=  userRepository.findByEmail(user.getEmail()).orElse(null);
          if(savedUser == null){
               userRepository.save(user);
               System.out.println("User saved Succesfully");
          } 

         new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile"); 
    }

}
