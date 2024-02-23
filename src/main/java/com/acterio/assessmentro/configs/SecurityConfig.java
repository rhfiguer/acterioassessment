package com.acterio.assessmentro.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/home").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/users").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/domain").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/login").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE,"/delete").permitAll();


                    auth.anyRequest().permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
