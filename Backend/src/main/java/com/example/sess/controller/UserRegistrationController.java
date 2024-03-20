package com.example.sess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sess.dto.UserRegistrationDto;
import com.example.sess.models.User;
import com.example.sess.services.UserService;

@RestController
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.registerUser(registrationDto.getUsername(), registrationDto.getPassword(),
                registrationDto.getEmail(),
                registrationDto.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
