package com.example.ecom.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.dto.TokenResponseDto;
import com.example.ecom.dto.UserDTO;
import com.example.ecom.exception.TokenVerificationException;
import com.example.ecom.service.JwtService;
import com.example.ecom.service.SignUpService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
// @CrossOrigin(origins = {"http://localhost:5173","https://ecom-java-backend-ad4067ce2405.herokuapp.com"}, allowedHeaders = "*", allowCredentials = "true")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/")
    public String welcomeMessage() {
        return "Welcome to the Example E-commerce Application";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userDTO) {
        try {
            Map<String, String> tokens = jwtService.generateTokens(userDTO);

            // Create a new HttpHeaders object
            HttpHeaders headers = new HttpHeaders();
            // Add the access token to the headers as a HTTP-only cookie
            headers.add(HttpHeaders.SET_COOKIE,
                    "access_token=" + tokens.get("accessToken") + "; HttpOnly; Secure ; Path=/; SameSite=None");

            return ResponseEntity.ok().headers(headers).body(Map.of("refreshToken", tokens.get("refreshToken")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Clear the HTTP-only cookie by setting its expiration date to a past date
        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Set expiration to 0 to delete the cookie
        cookie.setPath("/"); // Set the cookie path
        response.addCookie(cookie);
        // // Set the 'Access-Control-Allow-Origin' header with the specific origin
        // response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        // // Set the 'Access-Control-Allow-Credentials' header to 'true'
        // response.setHeader("Access-Control-Allow-Credentials", "true");

        // Optionally, invalidate the user session or access token on the server side

        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody TokenResponseDto tokenResponseDto) {
        try {
            Map<String, String> tokens = jwtService.getAccessTokenFromRefreshToken(tokenResponseDto);

            // Create a new HttpHeaders object
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE,
                    "access_token=" + tokens.get("accessToken") + "; HttpOnly; Path=/; SameSite=None");

            return ResponseEntity.ok().headers(headers).body(Map.of("refreshToken", tokens.get("refreshToken")));
        } catch (TokenVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        try {
            String status = signUpService.signUp(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User signed up successfully: " + status);
        } catch (Exception e) {
            // Return an error response with an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to sign up user: " + e.getMessage());
        }
    }
}
