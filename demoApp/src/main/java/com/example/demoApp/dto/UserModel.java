package com.example.demoApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserModel {
    private Long id;
    @NotBlank(message = "Name Cannot be blank")
    @Size(min =2, message = "Name should have more than 2 chars")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Enter a valid email")
    private String email;

    public UserModel(){

    }
    public UserModel(String name, String email){
        this.name = name;
        this.email = email;
    }

    public UserModel(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email=email;
    }
}
