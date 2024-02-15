package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequest {
    private Long userId;
    private Long productId;
    private int quantity;

}
