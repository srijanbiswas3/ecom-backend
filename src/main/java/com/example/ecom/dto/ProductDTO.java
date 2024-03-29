package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String details;
    private double price;
    private double discountedPrice;
    private int quantity;
    private String imageURL;
   
    private String gender;
    private String size;
    private String color;
    private Long categoryId;
    private Long brandId;
    private Long sellerId;
}
