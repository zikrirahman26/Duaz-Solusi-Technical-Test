package com.example.code.service;

import com.example.code.dto.UserRegisterRequest;
import com.example.code.dto.UserResponse;
import com.example.code.dto.UserUpdateRequest;
import com.example.code.entity.User;

public interface UserService {

    void registerUser(UserRegisterRequest request);

    UserResponse getUser(User user);

    UserResponse updateUser(UserUpdateRequest request, User user);
}
