package com.example.demoApp.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demoApp.dto.*;
import com.example.demoApp.entity.*;
import com.example.demoApp.exception.UserAlreadyExistsException;
import com.example.demoApp.exception.UserNotFoundException;
import com.example.demoApp.repository.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService( UserRepository userRepository){
        this.userRepository = userRepository;
    }
    // public UserModel getUser(){
    //     return new UserModel(1L, "Pragati", "pragati@example.com");
    // }

    public UserModel createUser(UserModel userModel){
        Long reqID = userModel.getId();
        if(userRepository.existsById(reqID)==true){
           throw new UserAlreadyExistsException("Cannot create user, user already exists");
        }
        UserEntity u = new UserEntity();
        u.setId(userModel.getId());
        u.setEmail(userModel.getEmail());
        u.setName(userModel.getName());

        UserEntity savedEntity = userRepository.save(u);

        return new UserModel(savedEntity.getId(), savedEntity.getName(), savedEntity.getEmail());
    }

    public List<UserEntity> returnAll(){
        return userRepository.findAll();
    }

    public String deleteById(Long id){
        if (userRepository.existsById(id) == false){
            throw new UserNotFoundException("The user with provided ID does not exist!");
        }
        else{
            userRepository.deleteById(id);
            return "Deleted ID "+id;
        }
    }
}
