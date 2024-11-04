package com.SCM.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SCM.repository.UserRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService{
    
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return  userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("username not found" + username)); 
    }

}