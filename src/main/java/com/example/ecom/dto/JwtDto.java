package com.example.ecom.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    private Long userId;
    private String refreshToken;
    private Date expiryTime;

}
