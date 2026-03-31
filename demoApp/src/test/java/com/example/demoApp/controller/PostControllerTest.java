package com.example.demoApp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demoApp.dto.PostModel;
import com.example.demoApp.entity.UserEntity;
import com.example.demoApp.service.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    public void TestPost() throws Exception{

        UserEntity userEntity = new UserEntity("PK", "pgs@gmail.com");
        userEntity.setId(1L);
        PostModel postModel = new PostModel(10L, "new post" , "Test post", userEntity);

        when(postService.createPost(userEntity.getId(), postModel.getTitle(), postModel.getContent()))
        .thenReturn(postModel);

        mockMvc.perform(post("/post/{userId}", 1L)
        .contentType("application/json")
        .content("""
                {
                    "title" : "new post",
                    "content": "Test post"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10L))
        .andExpect(jsonPath("$.title").value("new post"));
    }
    
}
