package com.example.sess.models;

import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.lang.NonNull;

@Entity
@Table(name = "person1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "user_name", unique = true)
    @NonNull
    private String username;

    @Column(name = "email", unique = true)
    @NonNull
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @NonNull
    @JsonIgnore
    private String passwordHash;

    public User(String username, String email, String role, String passwordHash) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    @NonNull
    public String getUsername() {
        return this.username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getRole() {
        return this.role;
    }

    public void setRole(@NonNull String role) {
        this.role = role;
    }

    @NonNull
    public String getEmail() {
        return this.email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPassword(@NonNull String password) {
        this.passwordHash = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(role, user.role) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role, email);
    }

}