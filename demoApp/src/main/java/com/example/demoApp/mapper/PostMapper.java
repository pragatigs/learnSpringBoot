package com.example.demoApp.mapper;

import com.example.demoApp.dto.PostModel;
import com.example.demoApp.dto.UserModel;
import com.example.demoApp.entity.PostEntity;

public class PostMapper {
    public PostEntity toEntity(PostModel postModel){
        return new PostEntity(postModel.getTitle(), postModel.getContent());
    }

    // public PostModel toModel(PostEntity postEntity, UserModel userModel){
    //     return new PostModel(postEntity.getTitle(), postEntity.getContent(), userModel); 
    // }
}
