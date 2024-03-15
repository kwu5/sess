package com.example.sess.Secutiry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserRegistrationAndAuthenticationFlow() throws Exception {
        // Step 1: Register a new user
        // mockMvc.perform(post("/register")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(
        // "{\"username\":\"testUser\",
        // \"password\":\"testPass\",\"email\":\"test@example.com\",
        // \"role\":\"ROLE_USER\"}"))
        // .andExpect(status().isOk());

        // Step 2: Authenticate the user and get the JWT token
        String responseBody = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", \"password\":\"testPass\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // String token = /* Extract token from the responseBody */;

        // // Step 3: Attempt to access a protected endpoint with the JWT token
        // mockMvc.perform(get("/protected-endpoint")
        // .header("Authorization", "Bearer " + token))
        // .andExpect(status().isOk());
        // You can add more assertions here to validate the response from the protected
        // endpoint
    }
}
