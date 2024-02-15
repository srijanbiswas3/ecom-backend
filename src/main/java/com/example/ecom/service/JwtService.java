package com.example.ecom.service;

import java.util.Map;

import com.example.ecom.dto.TokenResponseDto;
import com.example.ecom.dto.UserDTO;

public interface JwtService {

    public Map<String, String> generateTokens(UserDTO userDTO) throws Exception;

    public Map<String, String> generateAccessToken(TokenResponseDto tokenResponseDto);

}
