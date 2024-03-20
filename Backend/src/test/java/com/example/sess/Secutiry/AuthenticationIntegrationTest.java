package com.example.sess.Secutiry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testUserRegistrationAndAuthenticationFlow() throws Exception {
        // Step 1: Register a new user
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"username\":\"testUser\",\"password\":\"testPass\",\"email\":\"test@example.com\",\"role\":\"ROLE_USER\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        // Step 2: Authenticate the user and get the JWT token
        String loginData = "{ \"username\": \"testUser\", \"password\": \"testPass\" }";
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginData))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization")); // Assuming JWT is returned in the Authorization header
        // .andExpect(jsonPath("$.jwt").exists()); // Adjust this based on how your JWT
        // token is returned in the
        // response

    }
}
