package com.example.code.controller;

import com.example.code.dto.UserRegisterRequest;
import com.example.code.dto.UserResponse;
import com.example.code.dto.UserUpdateRequest;
import com.example.code.dto.WebResponse;
import com.example.code.entity.User;
import com.example.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public WebResponse<String> registerUser(@RequestBody UserRegisterRequest request){
        userService.registerUser(request);
        return WebResponse.<String>builder().data("success").build();
    }

    @GetMapping
    public WebResponse<UserResponse> getUser(User user){
        UserResponse response = userService.getUser(user);
        return WebResponse.<UserResponse>builder().data(response).build();
    }

    @PatchMapping
    public WebResponse<UserResponse> getUser(@RequestBody UserUpdateRequest request, User user){
        UserResponse response = userService.updateUser(request, user);
        return WebResponse.<UserResponse>builder().data(response).build();
    }
}
