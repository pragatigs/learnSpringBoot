package com.example.demoApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demoApp.dto.UserModel;
import com.example.demoApp.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService ;
    
    @Test
    public void TestPost() throws Exception{

        //mock response
        UserModel userModel = new UserModel(1L, "Pragati", "pgs@abc.com");

        //define behavior of mocked service

        when(userService.createUser(any(UserModel.class)))
        .thenReturn(userModel);

        //assert 

        mockMvc.perform(post("/user")
        .contentType("application/json")
        .content("""
                {
                "name": "Pragati",
                "email": "pgs@abc.com"
        }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Pragati"))
        .andExpect(jsonPath("$.email").value("pgs@abc.com"));
            

    }
}
