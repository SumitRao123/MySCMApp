package com.SCM.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SCM.entities.User;
import com.SCM.helpers.AppConstants;
import com.SCM.helpers.Helper;
import com.SCM.repository.UserRepository;
import com.SCM.services.EmailService;
import com.SCM.services.UserService;

@Service
public class UserServiceImpl implements UserService {
   
   @Autowired
   UserRepository userRepository;

//    private Logger logger = LoggerFactory.getLogger(this.getClass());
   
   @Autowired
   EmailService emailService;

   @Autowired
   PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserid(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE));
        System.out.println(user);
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepository.save(user);
        String emailLink = Helper.getEmailVerficationLink(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
        return savedUser;
    }

    @Override
    public Optional<User> getById(String userid) {
        // 
        return userRepository.findById(userid);
    }

    @Override
    public Optional<User> updateUser(User user) {
      
        User user1 = userRepository.findById(user.getUserid()).orElse(null);
        if(user1 != null){
             user1.setName(user.getName());
             user1.setEmail(user.getEmail());
             user1.setPassword(user.getPassword());
             user1.setAbout(user.getAbout());
            //  user1.setContacts(user.getContacts());
             user1.setEmailVerified(user.isEmailVerified());
             user1.setEnabled(user1.isEnabled());
             user1.setPhoneVerified(user.isPhoneVerified());
             user1.setProfilePic(user.getProfilePic());
             user1.setProvider(user.getProvider());
             user1.setProviderUserId(user.getProviderUserId());
             
             User saved = userRepository.save(user1);

             return Optional.ofNullable(saved);     
        }

        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(String  userid) {
     
        User user = userRepository.findById(userid).orElse(null);
        if(user != null){
             userRepository.delete(user);
        }
        
    }

    @Override
    public boolean isUserExist(String userid) {
      
        User user = userRepository.findById(userid).orElse(null);

        return (user != null) ? true : false;
        
    }

    @Override
    public boolean isUserExistByEmail(String email) {
          User user  = userRepository.findByEmail(email).orElse(null);
         return  (user != null) ? true : false;
    }

    @Override
    public List<User> getAllUser() {
        return  userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String username) {
     
       return userRepository.findByEmail(username).orElse(null);
    }

    @Override
    public User getEmailToken(String emailToken) {
         return userRepository.findByEmailToken(emailToken).orElse(null);
    }

    @Override
    public void saveVerifiedUser(User user) {
        user.setEmailVerified(true);
        user.setEnabled(true);
        userRepository.save(user);
    }

    
}
