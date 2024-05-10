package com.example.code.service.impl;

import com.example.code.dto.UserRegisterRequest;
import com.example.code.dto.UserResponse;
import com.example.code.dto.UserUpdateRequest;
import com.example.code.entity.User;
import com.example.code.repository.UserRepository;
import com.example.code.security.BCrypt;
import com.example.code.service.UserService;
import com.example.code.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public void registerUser(UserRegisterRequest request) {

        validationService.validate(request);

        User user = new User();
        if (userRepository.existsById(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exist");
        }

        user.setId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(User user) {
        return userResponse(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, User user) {

        validationService.validate(request);

        if (Objects.nonNull(request.getEmail())){
            user.setEmail(request.getEmail());
        }

        if (Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        if (Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }
        userRepository.save(user);

        return userResponse(user);
    }

    public UserResponse userResponse(User user){
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
