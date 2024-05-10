package com.example.code.service;

import com.example.code.dto.TokenResponse;
import com.example.code.dto.UserLoginRequest;
import com.example.code.entity.User;

public interface AuthService {

    TokenResponse login(UserLoginRequest request);
    void logout(User user);
}
