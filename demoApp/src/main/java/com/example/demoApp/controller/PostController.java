package com.example.demoApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoApp.dto.PostModel;
// import com.example.demoApp.entity.PostEntity;
import com.example.demoApp.service.PostService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService; 

    public PostController (PostService postService){
        this.postService = postService;
    }

    @PostMapping("/{userId}")
    public PostModel createNewPost(@RequestBody PostModel entity, @PathVariable Long userId) {
        PostModel resp = postService.createPost(userId, entity.getTitle(), entity.getContent());
        
        return resp;
    }
    
    
}
