package com.example.demoApp.mapper;

import com.example.demoApp.dto.UserModel;
import com.example.demoApp.entity.UserEntity;

public class UserMapper {
    public UserModel toModel(UserEntity userEntity){
        return new UserModel(userEntity.getName(), userEntity.getEmail());
    }

    public UserEntity toEntity(UserModel userModel){
        return new UserEntity(userModel.getName(), userModel.getEmail());
    }
}
