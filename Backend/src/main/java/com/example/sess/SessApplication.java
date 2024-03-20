package com.example.sess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.sess.models")
// @EnableJpaRepositories(basePackages = { "com.example.sess.dao" })
public class SessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessApplication.class, args);
    }

    // @Bean
    // public CommandLineRunner initializeUser(UserRepository userRepository,
    // BCryptPasswordEncoder passwordEncoder) {
    // return args -> {

    // User user = new User();
    // user.setUsername("john");
    // user.setEmail("john@gmail.com");
    // user.setPassword(passwordEncoder.encode("pwd123"));
    // user.setRole("USER");

    // User user1 = new User();
    // user.setUsername("jane");
    // user.setEmail("jane@gmail.com");
    // user.setPassword(passwordEncoder.encode("abc456"));
    // user.setRole("USER");

    // User user2 = new User();
    // user.setUsername("kyle");
    // user.setEmail("example@gmail.com");
    // user.setPassword(passwordEncoder.encode("admin"));
    // user.setRole("ADMIN");

    // // Save the user to the database
    // userRepository.save(user);
    // userRepository.save(user1);
    // userRepository.save(user2);

    // };
    // }

}
