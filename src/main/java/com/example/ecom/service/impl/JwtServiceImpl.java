package com.example.ecom.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ecom.dto.TokenResponseDto;
import com.example.ecom.dto.UserDTO;
import com.example.ecom.entity.Jwt;
import com.example.ecom.entity.User;
import com.example.ecom.exception.TokenVerificationException;
import com.example.ecom.repository.JwtRepository;
import com.example.ecom.repository.UsersRepository;
import com.example.ecom.service.JwtService;
import com.example.ecom.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.ACCESS_TOKEN_EXPIRATION}")
    private long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.REFRESH_TOKEN_EXPIRATION}")
    private long REFRESH_TOKEN_EXPIRATION;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Map<String, String> generateTokens(UserDTO userDTO) throws JsonProcessingException {

        User user = usersRepository.findByEmail(userDTO.getEmail());
        if (user == null) {
            return Map.of("error", "No User Found");
        }

        if (!BCrypt.checkpw(userDTO.getPassword(), user.getPassword())) {

            return Map.of("error", "Password Not Matched");
        }
        String accessToken = jwtUtils.generateToken(user, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = UUID.randomUUID().toString();
        // String hashedRefreshToken = BCrypt.hashpw(refreshToken, BCrypt.gensalt());

        Jwt jwt = jwtRepository.findByUser(user);
        if (jwt != null) {
            jwt.setRefreshToken(refreshToken);
            jwt.setExpiryTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION).toString());
            jwtRepository.save(jwt);
        } else {
            jwt = Jwt.builder()
                    .user(user)
                    .expiryTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION).toString())
                    .refreshToken(refreshToken)
                    .build();

            // jwt.setUser(user);
            // jwt.setRefreshToken(refreshToken);
            // jwt.setExpiryTime();
            jwtRepository.save(jwt);
        }

        Map<String, String> tokens = new HashMap<>();
        // ObjectMapper objectMapper = new ObjectMapper();
        // String userInfoJson = objectMapper.writeValueAsString(user);
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    @Override
    public Map<String, String> getAccessTokenFromRefreshToken(TokenResponseDto tokenResponseDto) {

        // String hashedRefreshToken = BCrypt.hashpw(tokenResponseDto.getRefreshToken(),
        // BCrypt.gensalt());
        Jwt jwtEntity = jwtRepository.findByRefreshToken(tokenResponseDto.getRefreshToken());
        User user = usersRepository.findById(jwtEntity.getUser().getId()).get();
        // Jwt jwt = jwtRepository.findByUser(user);

        if (!tokenResponseDto.getRefreshToken().equals(jwtEntity.getRefreshToken())) {
            throw new TokenVerificationException("Refresh token Donnt match");
        }
        // if (!BCrypt.checkpw(tokenResponseDto.getRefreshToken(),
        // jwt.getRefreshToken())) {
        // return Map.of("error", "Refresh Token Not Matched");
        // }

        String newRefreshToken = UUID.randomUUID().toString();
        // String newHashedRefreshToken = BCrypt.hashpw(newRefreshToken,
        // BCrypt.gensalt());
        // Jwt jwt=new Jwt();
        jwtEntity.setRefreshToken(newRefreshToken);
        jwtEntity.setExpiryTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION).toString());
        jwtRepository.save(jwtEntity);

        String newAccessToken = jwtUtils.generateToken(user, ACCESS_TOKEN_EXPIRATION);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);
        return tokens;
    }

}
