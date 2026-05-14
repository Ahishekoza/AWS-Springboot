package com.spring.security_6.configuration;

import com.spring.security_6.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtAuthenticationConf jwtAuthenticationConf;

    public SecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, @Lazy JwtAuthenticationConf jwtAuthenticationConf) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtAuthenticationConf = jwtAuthenticationConf;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())   // Disable for now (you can enable later)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/api/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationConf, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}