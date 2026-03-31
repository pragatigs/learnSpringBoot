package com.example.demoApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import com.example.demoApp.dto.PostModel;
import com.example.demoApp.entity.PostEntity;
import com.example.demoApp.entity.UserEntity;
import com.example.demoApp.exception.UserNotFoundException;
import com.example.demoApp.repository.PostRepository;
import com.example.demoApp.repository.UserRepository;

// import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest{

    // create mocks
    // UserRepository userRepository = mock(UserRepository.class);
    
    // PostRepository postRepository = mock(PostRepository.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    public void TestCreatePost(){
    
    //create test data
    UserEntity user = new UserEntity("P", "pgs@rediff.com");
    user.setId(1L);


    //define behavior
    when(userRepository.findById(1L))
        .thenReturn(Optional.of(user));

    when(postRepository.save(any(PostEntity.class)))
    .thenAnswer(invocation -> {
        PostEntity entity = invocation.getArgument(0);
        entity.setId(11L);
        return entity;
    });

    // create service
    // PostService  postService = new PostService(userRepository, postRepository);

    PostModel res = postService.createPost(1L, "First test", "Testing using Junit");

    assertNotNull(res.getUser());
    assertEquals(11L, res.getId());
    assertEquals("First test", res.getTitle());


    }

    public void TestUserNotfound(){
    
        //dont create user test data for this

        // define behavior
        when(userRepository.findById(1L))
        .thenThrow(UserNotFoundException.class);
    }

}
