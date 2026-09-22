package com.example.securemvc.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private static final long MAX_SIZE = 2 * 1024 * 1024;
    private static final Set<String> ALLOWED = Set.of("png", "jpg", "jpeg", "pdf");

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getSize() > MAX_SIZE) {
            return ResponseEntity.badRequest().body("File is empty or too large");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String ext = StringUtils.getFilenameExtension(original);
        if (ext == null || !ALLOWED.contains(ext.toLowerCase())) {
            return ResponseEntity.badRequest().body("File type is not allowed");
        }

        // Generate our own server-side filename; never trust the client filename.
        String safeName = java.util.UUID.randomUUID() + "." + ext.toLowerCase();
        Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
        Path target = uploadDir.resolve(safeName).normalize();

        if (!target.getParent().equals(uploadDir)) {
            return ResponseEntity.badRequest().body("Invalid file path");
        }

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok("File uploaded safely");
    }
}
