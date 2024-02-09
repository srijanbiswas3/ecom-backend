package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private String name;
    private String email;
    private String password;
    private String role;
    private String address;
    private String phone;

}
