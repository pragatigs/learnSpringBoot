package com.example.demoApp.service;

import org.springframework.stereotype.Service;

import com.example.demoApp.dto.PostModel;
import com.example.demoApp.entity.PostEntity;
import com.example.demoApp.entity.UserEntity;
import com.example.demoApp.exception.UserNotFoundException;
import com.example.demoApp.mapper.PostMapper;
import com.example.demoApp.repository.PostRepository;
import com.example.demoApp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
    
    private final UserRepository userRepository ;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @Transactional
    public PostModel createPost (Long userId, String title, String content){
        UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User does not exists, create first!"));

        // PostMapper mapper = new PostMapper();
        PostEntity newPost = new PostEntity(title, content);

        newPost.setUser(user);
       
        PostEntity savePost = postRepository.save(newPost);

        return new PostModel(savePost.getId(), savePost.getTitle(), savePost.getContent(), savePost.getUser());
    }
}
