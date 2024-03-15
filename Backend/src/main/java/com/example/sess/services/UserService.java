package com.example.sess.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sess.dao.UserRepository;
import com.example.sess.models.User;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User registerUser(String username, String password, String email, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, role, encodedPassword);
        return userRepository.save(newUser);
    }

    // @Transactional
    // public User createUser(String username, String rawPassword, String email,
    // String role) {
    // User newUser = new User();
    // newUser.setUsername(username);
    // newUser.setPassword(passwordEncoder.encode(rawPassword));
    // newUser.setRole(role);
    // newUser.setEmail(email);
    // // Add other default settings or validations if needed

    // System.out.println(newUser.getUsername() + " is Saving");
    // return userRepository.save(newUser);
    // }

    // @Transactional
    // public User updateUser(Long userId, String newusername, String
    // newRawPassword, String newRole) {
    // User existingUser = userRepository.findById(userId)
    // .orElseThrow(() -> new RuntimeException("User not found"));

    // existingUser.setUsername(newusername);
    // existingUser.setPassword(passwordEncoder.encode(newRawPassword));
    // existingUser.setRole(newRole);
    // // Implement other changes or business logic as needed

    // return userRepository.save(existingUser);
    // }

    public Long getUserId(String name) throws UsernameNotFoundException {

        logger.info("getUserId: " + name + "runs");
        Optional<User> userOpt = userRepository.findByUsername(name);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            logger.info("getUserId: " + name + "get: " + user.getId());
            return user.getId();
        } else {
            // Handle the case where user is not found
            throw new UsernameNotFoundException("User not found with username: " + name);
        }

    }

    // // public User getUserByusername(String name){
    // // return userRepository.findByusername(name);
    // // }

    // public boolean isAdmin(String username) {
    // return userRepository.findByUsername(username)
    // .map(user -> user.getRole())
    // .map(String::toUpperCase) // assuming role is stored as uppercase
    // .filter(role -> role.equals("ADMIN"))
    // .isPresent();
    // }

}
