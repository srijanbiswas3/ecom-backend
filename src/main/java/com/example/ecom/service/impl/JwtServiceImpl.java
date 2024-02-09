package com.example.ecom.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ecom.dto.TokenResponseDto;
import com.example.ecom.dto.UsersDto;
import com.example.ecom.entity.Jwt;
import com.example.ecom.entity.User;
import com.example.ecom.repository.JwtRepository;
import com.example.ecom.repository.UsersRepository;
import com.example.ecom.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    private long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 30;
    private long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Map<String, String> generateTokens(UsersDto usersDto) {
        if (!usersRepository.existsByEmail(usersDto.getEmail())) {
            return Map.of("error", "No User Found");
        }
        
        User user = usersRepository.findByEmail(usersDto.getEmail());
        if (!BCrypt.checkpw(usersDto.getPassword(), user.getPassword())) {
            
            return Map.of("error", "Password Not Matched");
        }
        String accessToken = generateToken(user, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = UUID.randomUUID().toString();

        Jwt jwt = jwtRepository.findByUser(user);
        if (jwt != null) {
            jwt.setRefreshToken(refreshToken);
            jwt.setExpiryTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION));
            jwtRepository.save(jwt);
        } else {
            jwt = new Jwt();
            jwt.setUser(user);
            jwt.setRefreshToken(refreshToken);
            jwt.setExpiryTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION));
            jwtRepository.save(jwt);
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    private String generateToken(User user, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("email", user.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSignKey() {
        byte[] secretEncoded = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(secretEncoded);

    }

    @Override
    public Map<String, String> generateAccessToken(TokenResponseDto tokenResponseDto) {
        if (!verifyToken(tokenResponseDto.getAccessToken())) {
            return Map.of("error", "Invalid Access Token");
        }

        Claims claims = extractClaims(tokenResponseDto.getAccessToken());
        String email = claims.getSubject();

        if (!usersRepository.existsByEmail(email)) {
            return Map.of("error", "User Not Found");
        }

        User user = usersRepository.findByEmail(email);
        Jwt jwt = jwtRepository.findByUser(user);

        if (jwt == null || !jwt.getRefreshToken().equals(tokenResponseDto.getRefreshToken())) {
            return Map.of("error", "Invalid Refresh Token");
        }

        String newRefreshToken = UUID.randomUUID().toString();
        jwt.setRefreshToken(newRefreshToken);
        jwtRepository.save(jwt);

        String newAccessToken = generateToken(user, ACCESS_TOKEN_EXPIRATION);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);
        return tokens;
    }

    public Claims extractClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
