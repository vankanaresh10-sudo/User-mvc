package com.example.securemvc.config;

import com.example.securemvc.model.AppUser;
import com.example.securemvc.model.Role;
import com.example.securemvc.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            String adminPassword = System.getenv().getOrDefault("DEMO_ADMIN_PASSWORD", "ChangeMe_123!");
            if (!users.existsByUsername("admin")) {
                users.save(new AppUser("admin", encoder.encode(adminPassword), Role.ADMIN));
            }
        };
    }
}
