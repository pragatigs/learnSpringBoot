package com.example.demoApp.repository;

import com.example.demoApp.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.posts")
    List<UserEntity> findAllWithPosts();
} 