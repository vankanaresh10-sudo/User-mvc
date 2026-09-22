package com.example.securemvc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/api/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard() {
        return "Admin dashboard";
    }
}
