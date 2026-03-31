package com.example.demoApp.controller;
import com.example.demoApp.dto.UserModel;
import com.example.demoApp.entity.UserEntity;
import com.example.demoApp.service.*;

import jakarta.validation.Valid;

import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController{
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public UserModel createUser(@Valid @RequestBody UserModel userModel){
        UserModel createdUser = userService.createUser(userModel);
        return createdUser;
    }

    @GetMapping
    public List<UserEntity> getAll(){
        return userService.returnAll();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id){
        return userService.DeleteById(id);
    }
    
}