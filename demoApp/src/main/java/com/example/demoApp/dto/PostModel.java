package com.example.demoApp.dto;

import com.example.demoApp.entity.UserEntity;

public class PostModel {
    private Long id;
    private String title;
    private String content;
    private UserEntity user;

    public PostModel(){

    }

    public PostModel(String title, String content, UserEntity user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public PostModel(Long id, String title, String content, UserEntity user){
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public UserEntity getUser(){
        return user;
    }

    public void setUser(UserEntity user){
        this.user = user;
    }
    
}
