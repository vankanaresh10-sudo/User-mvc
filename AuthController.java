package com.example.securemvc.controller;

import com.example.securemvc.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 80) String username,
        @NotBlank @Size(min = 8, max = 72) String password
    ) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@jakarta.validation.Valid @RequestBody RegisterRequest request) {
        userService.register(request.username(), request.password());
        return ResponseEntity.ok().body("Registration successful");
    }
}
