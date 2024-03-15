package com.example.sess;

import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.core.userdetails.User;
import org.mockito.Mockito;

import com.example.sess.controller.AuthenticationController;
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
    private UserDetailsService userDetailsService;

    @Test
    public void shouldReturnJWTWhenAuthenticationIsSuccessfull() throws Exception {
        String username = "user";
        String jwt = "dummyJwtToken";
        String password = "password";

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        Mockito.when(jwtUtil.generateToken(username)).thenReturn(jwt);

        // given(jwtUtil.generateToken(username)).willReturn(jwt);
        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value(jwt));

    }

}
