package com.ecommerce.shopify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    // create a bean for bcrypt pw encoder and use it in user service
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt with strength 10 (default)
        return new BCryptPasswordEncoder();
    }
}
