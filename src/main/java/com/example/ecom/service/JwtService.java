package com.example.ecom.service;

import java.util.Map;

import com.example.ecom.dto.TokenResponseDto;
import com.example.ecom.dto.UsersDto;

public interface JwtService {

    // public String generateToken(String userName);
    public Boolean verifyToken(String token);

    public Map<String, String> generateTokens(UsersDto usersDto) throws Exception;

    public Map<String, String> generateAccessToken(TokenResponseDto tokenResponseDto);

}
