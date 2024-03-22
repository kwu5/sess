package com.example.sess.dto;

public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
    private String role;

    public UserRegistrationDto(String username, String email, String role, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;

    }

    public String getRole() {
        return role;

    }

    public String getEmail() {
        return email;

    }

    public String getPassword() {
        return password;

    }

}
