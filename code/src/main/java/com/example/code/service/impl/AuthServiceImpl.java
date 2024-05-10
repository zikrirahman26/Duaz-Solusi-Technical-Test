package com.example.code.service.impl;

import com.example.code.dto.TokenResponse;
import com.example.code.dto.UserLoginRequest;
import com.example.code.entity.User;
import com.example.code.repository.UserRepository;
import com.example.code.security.BCrypt;
import com.example.code.service.AuthService;
import com.example.code.service.ValidationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Override
    public TokenResponse login(UserLoginRequest request) {

        validationService.validate(request);

        User user = userRepository.findFirstByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "email not found"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())){

            String token = createToken(request.getEmail());
            user.setToken(token);
            user.setTokenExpired(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password wrong");
        }
        return TokenResponse.builder()
                .token(user.getToken())
                .tokenExpired(user.getTokenExpired())
                .build();
    }

    @Override
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpired(null);
        userRepository.save(user);
    }

    private String createToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
