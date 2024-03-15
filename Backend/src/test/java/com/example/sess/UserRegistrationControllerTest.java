package com.example.sess;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

// import com.example.sess.util.JwtUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sess.config.SecurityConfig;
import com.example.sess.controller.UserRegistrationController;
import com.example.sess.dto.UserRegistrationDto;
import com.example.sess.models.User;
import com.example.sess.services.UserService;
import com.example.sess.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(UserRegistrationController.class)
@WebMvcTest(controllers = UserRegistrationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserRegistrationControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserService userService;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private AuthenticationManager authenticationManager;

        @MockBean
        private JwtUtil jwtUtil;

        @Test
        public void shouldReturnUserWhenRegistrationIsSuccessful() throws Exception {

                UserRegistrationDto registrationDto = new UserRegistrationDto("username", "email@example.com",
                                "ROLE_USER",
                                "passwordHash");
                User registratedUser = new User();
                registratedUser.setUsername(registrationDto.getUsername());

                given(userService.registerUser(any(String.class), any(String.class), any(String.class),
                                any(String.class)))
                                .willReturn(registratedUser);

                mockMvc.perform(post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registrationDto)))
                                .andExpect(status().isOk());

        }

}
