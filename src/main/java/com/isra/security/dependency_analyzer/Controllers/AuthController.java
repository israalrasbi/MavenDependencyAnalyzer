package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.DTOs.SignInRequestDTO;
import com.isra.security.dependency_analyzer.DTOs.SignUpRequestDTO;
import com.isra.security.dependency_analyzer.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDTO dto) {
        try {
            authService.signUp(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequestDTO dto) {
        try {
            String token = authService.signIn(dto);
            return ResponseEntity.ok(Map.of("token",token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}