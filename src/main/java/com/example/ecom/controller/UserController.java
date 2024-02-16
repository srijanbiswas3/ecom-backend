package com.example.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.exception.TokenExpiredException;
import com.example.ecom.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<String> getUserInfo(
            @CookieValue(name = "access_token", required = false) String accessToken) {

        if (accessToken != null) {
            try {
                // Here you can use the access token to retrieve user information
                String user = userService.getUserInfo(accessToken);
                return ResponseEntity.ok(user);
            } catch (TokenExpiredException e) {
                // Access token has expired
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token has expired");
            } catch (Exception e) {
                // Other exceptions
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not found");
        }

    }
}
