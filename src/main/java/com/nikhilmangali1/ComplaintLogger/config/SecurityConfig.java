package com.nikhilmangali1.ComplaintLogger.config;

import com.nikhilmangali1.ComplaintLogger.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/user/register","/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home", "/").permitAll()

                        .requestMatchers(HttpMethod.GET, "/user/getUserWithComplaints/**").hasAnyRole("USER", "ADMIN")


                        .requestMatchers(HttpMethod.DELETE, "/user/deleteUser/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/users").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.PUT, "/user/updateUser/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/user/username/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/complaints/raiseComplaint").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/complaints/deleteComplaint/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/complaints/updateComplaint/**", "/complaints/withdrawComplaint/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/complaints/getComplaint/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/complaints/all", "/complaints/category/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}