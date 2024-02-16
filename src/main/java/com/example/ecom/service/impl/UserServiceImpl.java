package com.example.ecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.entity.User;
import com.example.ecom.exception.AuthenticationException;
import com.example.ecom.exception.TokenExpiredException;
import com.example.ecom.exception.TokenVerificationException;
import com.example.ecom.exception.UserNotFoundException;
import com.example.ecom.repository.UsersRepository;
import com.example.ecom.service.UserService;
import com.example.ecom.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UsersRepository usersRepository;

    @Override
    public String getUserInfo(String token) {
        String userInfoJson = null;
        try {
            jwtUtils.verifyToken(token);//throws exception if not verified

            Long userId = Long.parseLong(jwtUtils.extractClaims(token).get("userId").toString());

            User user = usersRepository.findById(userId).orElse(null);

            if (user == null) {
                throw new UserNotFoundException("User not found");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            userInfoJson = objectMapper.writeValueAsString(user);

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Access token has expired");
        } catch (TokenVerificationException e) {
            throw new AuthenticationException("Invalid token");
        } catch (UserNotFoundException e) {
            throw new AuthenticationException("User not found");
        } catch (Exception e) {
            throw new AuthenticationException("An error occurred while fetching user info");
        }
        return userInfoJson;
    }

}
