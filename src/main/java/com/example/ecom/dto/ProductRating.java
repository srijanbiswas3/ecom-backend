package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRating {
    private long id;
    private double avgRating;
    private long userCount;

    public ProductRating(double avgRating, long userCount) {
        this.avgRating = avgRating;
        this.userCount = userCount;
    }



}
