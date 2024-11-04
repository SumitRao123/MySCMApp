package com.SCM.services;

import java.util.*;

import org.springframework.data.domain.Page;

import com.SCM.entities.*;

public interface ContactService {
   
    Contact save(Contact contact);
    
    Contact update(Contact contact);

    List<Contact> getAll();
   
    void delete(String id);

    
    Contact getById(String id);
    
    Page<Contact> getByUser(User user, int page,int size,String sortBy,String direction);

    Page<Contact> searchByName(User user,String name,int page, int size, String sortBy,String direction);

    Page<Contact> searchByEmail(User user,String email,int page, int size, String sortBy,String direction );
    Page<Contact> searchByPhoneNumber(User user,String phoneNumber,int page, int size, String sortBy,String direction );
}
