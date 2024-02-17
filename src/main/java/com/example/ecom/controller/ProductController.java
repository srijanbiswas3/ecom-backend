package com.example.ecom.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
// @CrossOrigin(origins = {"http://localhost:5173","https://ecom-java-backend-ad4067ce2405.herokuapp.com"}, allowedHeaders = "*", allowCredentials = "true")

public class ProductController {


    @GetMapping("/products")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    
}
