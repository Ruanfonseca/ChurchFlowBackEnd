package com.system.churchflow.services;


import com.system.churchflow.model.User;
import com.system.churchflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;


    public User findUser(String email){
        User userResponse = repository.findBylogin(email);

        return userResponse;
    }

    public UserDetails findByLogin(String email){
        Optional<UserDetails> userResponse = Optional.ofNullable(repository.findByLogin(email));

        if (userResponse.isPresent()) {
            return userResponse.get();
        } else{
             return null;
           }
        }


    public void save(User newUser) {
        repository.save(newUser);
    }
}
