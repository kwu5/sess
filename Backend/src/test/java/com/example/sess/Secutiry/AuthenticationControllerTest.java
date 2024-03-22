package com.example.sess.Secutiry;

import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mockito;

import com.example.sess.controller.AuthenticationController;
import com.example.sess.services.CustomUserDetailsService;
import com.example.sess.util.JwtUtil;

// @WebMvcTest(AuthenticationController.class)
@WebMvcTest(controllers = AuthenticationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)

public class AuthenticationControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private AuthenticationManager authenticationManager;

        @MockBean
        private JwtUtil jwtUtil;

        @MockBean
        private CustomUserDetailsService userDetailsService;

        @Test
        public void shouldReturnJWTWhenAuthenticationIsSuccessfull() throws Exception {
                String username = "user";
                String jwt = "dummyJwtToken";
                String password = "password";

                UserDetails userDetails = User.builder()
                                .username(username)
                                .password(password)
                                .authorities("ROLE_USER")
                                .build();

                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, password,
                                userDetails.getAuthorities());

                Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                .thenReturn(auth);
                Mockito.when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn(jwt);

                // given(jwtUtil.generateToken(username)).willReturn(jwt);
                mockMvc.perform(post("/login")
                                .contentType("application/json")
                                .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.jwt").value(jwt));

        }

}
