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
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getUsername'");
    }

    public String getRole() {
        return role;
        // throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }

    public String getEmail() {
        return email;
        // throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
    }

    public String getPassword() {
        return password;
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getPassword'");
    }

}
