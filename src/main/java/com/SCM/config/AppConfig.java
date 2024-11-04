package com.SCM.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {
     
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    
    @Value("${cloudinary.cloud.key}")
    private String api_key;

    @Value("${cloudinary.cloud.secret}")
    private String api_secret;
    
    @Bean
     public Cloudinary getCloud(){
         return  new Cloudinary(ObjectUtils.asMap("cloud_name",cloudName,
                                                   "api_key",api_key,
                                                   "api_secret",api_secret));

     }
}   
