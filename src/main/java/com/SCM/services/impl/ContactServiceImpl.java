package com.SCM.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.entities.Contact;
import com.SCM.repository.ContactRepository;
import com.SCM.services.ContactService;
import com.SCM.entities.User;

@Service
public class ContactServiceImpl implements ContactService{
    
    @Autowired
    ContactRepository contactRepository;


    @Override
    public Contact save(Contact contact) {
       
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
     
        var contactOld = contactRepository.findById(contact.getId())
        .orElse(null);
       contactOld.setName(contact.getName());
       contactOld.setEmail(contact.getEmail());
       contactOld.setPhoneNumber(contact.getPhoneNumber());
       contactOld.setAddress(contact.getAddress());
       contactOld.setDescription(contact.getDescription());
       contactOld.setPicture(contact.getPicture());
       contactOld.setFavourite(contact.isFavourite());
       contactOld.setWebSiteLink(contact.getWebSiteLink());
       contactOld.setLinkedInLink(contact.getLinkedInLink());
       contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

       return contactRepository.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {
   

        return contactRepository.findAll();
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        contactRepository.deleteById(id);
    }

    @Override
    public Contact getById(String id) {
        // TODO Auto-generated method stub
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Contact> getByUser(User user, int page,int size,String sortBy,String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByName(User user, String name, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(User user, String email, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndEmailContaining(user, email, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(User user, String phoneNumber, int page, int size, String sortBy,
            String direction) {
                Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
    }
   

}
