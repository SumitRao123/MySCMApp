package com.SCM.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.entities.User;

import java.lang.foreign.Linker.Option;
import java.util.Optional;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User,String> {
   
    Optional<User>  findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email,String password);

    Optional<User> findByEmailToken(String emailToken);
}
