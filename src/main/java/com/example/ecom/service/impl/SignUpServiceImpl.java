package com.example.ecom.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.dto.UsersDto;
import com.example.ecom.entity.User;
import com.example.ecom.repository.UsersRepository;
import com.example.ecom.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String signUp(UsersDto usersDto) {
        try {
            // Create a new user entity and populate it with data from the DTO
            User user = new User();
            user.setEmail(usersDto.getEmail());
            // Hash the password before storing it
            String hashedPassword = BCrypt.hashpw(usersDto.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
            user.setName(usersDto.getName());
            user.setAddress(usersDto.getAddress());
            user.setPhone(usersDto.getPhone());
            user.setRole(usersDto.getRole());

            // Save the user entity using the repository
            User savedUser = usersRepository.save(user);

            // Check if the save operation was successful
            if (savedUser != null) {
                return "success";
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the signup process
            e.printStackTrace();
            return "failed due to an unexpected error";
        }

        // If the save operation failed, return an appropriate failure message
        return "failed to save user";
    }

}
