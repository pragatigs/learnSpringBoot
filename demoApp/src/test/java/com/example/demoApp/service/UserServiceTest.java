package com.example.demoApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoApp.dto.UserModel;
import com.example.demoApp.entity.UserEntity;
import com.example.demoApp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    // UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService u;

    @Test
    public void TestCreateUser(){
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        // UserService u = new UserService(userRepository);

        UserModel uModel = new UserModel("P", "pgs@xyz.com");

        UserModel res = u.createUser(uModel);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        verify(userRepository).save(captor.capture());

        UserEntity savedUser = captor.getValue();

        assertEquals(savedUser.getEmail(), res.getEmail());
        assertEquals(res.getEmail(), "pgs@xyz.com");
        assertEquals(1L, res.getId());
        
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void TestReturnAll(){
        UserService u = new UserService(userRepository);
        UserEntity ue = new UserEntity("P", "pgs@xyz.com");
        ue.setId(1L);

        when(userRepository.findAll())
        .thenReturn(List.of(ue));

        assertEquals(1L, u.returnAll().get(0).getId());
    }

    @Test
    public void TestDeleteById(){
        UserService u = new UserService(userRepository);
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        String ans = u.DeleteById(1L);

        assertEquals("Deleted ID 1" , ans);
    }
}
