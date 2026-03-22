package com.example.demoApp.service;

import org.springframework.stereotype.Service;

import com.example.demoApp.dto.PostModel;
import com.example.demoApp.entity.PostEntity;
import com.example.demoApp.entity.UserEntity;
import com.example.demoApp.exception.UserNotFoundException;
import com.example.demoApp.repository.PostRepository;
import com.example.demoApp.repository.UserRepository;

@Service
public class PostService {
    
    private final UserRepository userRepository ;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    public PostModel createPost (Long id, String title, String content){
        UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User does not exists, create first!"));

        PostEntity newPost = new PostEntity(id, title, content);

        newPost.setUser(user);
       
        PostEntity savePost = postRepository.save(newPost);

        return new PostModel(savePost.getId(), savePost.getTitle(), savePost.getContent(), savePost.getUser());
    }
}
