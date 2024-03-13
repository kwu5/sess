package com.example.sess.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.sess.services.JpaUserDetailService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.RSAKey;
// import java.security.interfaces.RSAPublicKey;
// import java.security.interfaces.RSAPrivateKey;
import static org.springframework.security.config.Customizer.withDefaults;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.springframework.security.config.Customizer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private RsaKeyConfigProperties rsaKeyConfigProperties;
    @Autowired
    private JpaUserDetailService userDetailsService;

    @Bean
    public AuthenticationManager authManager() {

        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {

        // http.authorizeHttpRequests(auth -> auth
        // .requestMatchers("/home/**").hasAnyRole("ADMIN", "USER") // Access to home
        // page for both roles
        // .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-specific endpoints
        // // .requestMatchers("/tasks/**").hasAnyRole("ADMIN", "USER") // Task
        // endpoints
        // // accessible by both roles
        // .requestMatchers("/tasks/**").permitAll() // Task endpoints accessible by
        // both roles
        // .anyRequest().authenticated()) // Any other request must be authenticated
        // .csrf(csrf -> csrf.disable())
        // .sessionManagement(s ->
        // s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(decoder)))
        // .userDetailsService(userDetailsService)
        // .httpBasic(Customizer.withDefaults());
        // return http.build();

        return http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/home/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/tasks/**").hasAnyRole("ADMIN", "USER");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> {
                    try {
                        jwt.decoder(jwtDecoder(rsaKeyConfigProperties));
                    } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }))
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();

    }

    // @Bean
    // public JwtDecoder jwtDecoder() {
    // return
    // NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
    // }

    // @Bean
    // JwtEncoder jwtEncoder() {
    // JWK jwk = new
    // RSAKey.Builder(rsaKeyConfigProperties.getRSAPublicKey()).privateKey(rsaKeyConfigProperties.getRSAPrivateKey())
    // .build();

    // JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    // return new NimbusJwtEncoder(jwks);
    // }

    public JwtDecoder jwtDecoder(RsaKeyConfigProperties rsaKeyConfigProperties)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.getRSAPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(RsaKeyConfigProperties rsaKeyConfigProperties)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAKey.Builder builder = new RSAKey.Builder(rsaKeyConfigProperties.getRSAPublicKey())
                .privateKey(rsaKeyConfigProperties.getRSAPrivateKey()); // Ensure this is the correct method to obtain
                                                                        // the
        // privateKey
        JWK jwk = builder.build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
