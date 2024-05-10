package com.example.code.controller;

import com.example.code.dto.TokenResponse;
import com.example.code.dto.UserLoginRequest;
import com.example.code.dto.WebResponse;
import com.example.code.entity.User;
import com.example.code.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public WebResponse<TokenResponse> login(@RequestBody UserLoginRequest request){
        TokenResponse response = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(response).build();
    }

    @DeleteMapping("/logout")
    public WebResponse<String> logout(User user){
        authService.logout(user);
        return WebResponse.<String>builder().data("logout").build();
    }
}
