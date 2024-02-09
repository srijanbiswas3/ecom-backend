package com.example.ecom.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecom.dto.TokenResponseDto;
import com.example.ecom.dto.UsersDto;
import com.example.ecom.service.JwtService;
import com.example.ecom.service.SignUpService;

@RestController
@CrossOrigin("http://localhost:5173")
public class ProductController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/")
    public String welcomeMessage() {
        return "Welcome to the Example E-commerce Application";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UsersDto usersDto) {
        try {
            Map<String, String> tokens = jwtService.generateTokens(usersDto);
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody TokenResponseDto tokenResponseDto) {
        try {
            Map<String, String> accessToken = jwtService.generateAccessToken(tokenResponseDto);
            return ResponseEntity.ok(accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UsersDto usersDto) {
        try {
            String status = signUpService.signUp(usersDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User signed up successfully: " + status);
        } catch (Exception e) {
            // Return an error response with an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to sign up user: " + e.getMessage());
        }
    }
}
