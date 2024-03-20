package com.example.sess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.sess.security.JWTAuthenticationFilter;
import com.example.sess.security.JWTAuthorizationFilter;
import com.example.sess.services.CustomUserDetailsService;
import com.example.sess.util.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // @Autowired
        // private AuthenticationManager authenticationManager;

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        // public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        // this.customUserDetailsService = customUserDetailsService;
        // }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
                        throws Exception {

                JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager,
                                jwtUtil);
                JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(jwtUtil,
                                customUserDetailsService);

                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests((authz) -> authz
                                                .requestMatchers("/login", "/register").permitAll()
                                                .requestMatchers("/home").hasAnyRole("ADMIN", "USER")
                                                .requestMatchers("/tasks/**").hasAnyRole("Admin", "USER")
                                                .requestMatchers("/admin/**").hasAnyRole("Admin")
                                                .anyRequest().authenticated())
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .addFilter(jwtAuthenticationFilter)
                                .addFilterBefore(jwtAuthorizationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();

        }
}
