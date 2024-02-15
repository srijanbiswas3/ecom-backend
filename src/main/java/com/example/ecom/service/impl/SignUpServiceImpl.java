package com.example.ecom.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.dto.UserDTO;
import com.example.ecom.entity.Address;
import com.example.ecom.entity.User;
import com.example.ecom.repository.UsersRepository;
import com.example.ecom.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String signUp(UserDTO userDTO) {
        try {
            // Check if a user with the same email already exists
            if (usersRepository.findByEmail(userDTO.getEmail()) != null) {
                return "failed: email already exists";
            }

            // Create a new user entity and populate it with data from the DTO
            Address address = new Address();
            address.setAddressLine1(userDTO.getAddress().getAddressLine1());
            address.setAddressLine2(userDTO.getAddress().getAddressLine2());
            address.setCity(userDTO.getAddress().getCity());
            address.setCountry(userDTO.getAddress().getCountry());
            address.setState(userDTO.getAddress().getState());
            address.setZipCode(userDTO.getAddress().getZipCode());

            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());// Hash the password before
                                                                                           // storing it

            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setPassword(hashedPassword);
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setAddress(address);
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setUserType(userDTO.getUserType());

            // Save the user entity using the repository
            User savedUser = usersRepository.save(user);

            // Check if the save operation was successful
            if (savedUser != null) {
                return "success";
            } else {
                return "failed to save user";
            }
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            e.printStackTrace();
            return "failed due to an unexpected error";
        }
    }

}
