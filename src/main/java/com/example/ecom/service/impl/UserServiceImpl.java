package com.example.ecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.entity.User;
import com.example.ecom.exception.TokenVerificationException;
import com.example.ecom.exception.UserNotFoundException;
import com.example.ecom.repository.UsersRepository;
import com.example.ecom.service.UserService;
import com.example.ecom.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            if (!jwtUtils.verifyToken(token)) {
                throw new TokenVerificationException("Invalid token");
            }
            Long userId = Long.parseLong(jwtUtils.extractClaims(token).get("userId").toString());

            User user = usersRepository.findById(userId).get();

            if (user == null) {
                throw new UserNotFoundException("User not Found");
            }
            ObjectMapper objectMapper = new ObjectMapper();

            userInfoJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return userInfoJson;

    }

}
