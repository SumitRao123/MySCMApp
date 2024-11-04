package com.SCM.services;

import java.util.List;
import java.util.Optional;

import com.SCM.entities.User;

public interface UserService {
   
    public User saveUser(User user);

    Optional<User> getById(String userid);
    Optional<User> updateUser(User user);
    
    void deleteUser(String userid);

    boolean isUserExist(String userid);
    boolean isUserExistByEmail(String  email);

    List<User> getAllUser();
    User getUserByEmail(String username);

    User getEmailToken(String emailToken);

    void saveVerifiedUser(User user);
}
