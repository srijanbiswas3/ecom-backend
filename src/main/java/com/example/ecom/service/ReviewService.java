package com.example.ecom.service;

import java.util.List;

import com.example.ecom.dto.ProductRating;
import com.example.ecom.entity.Review;

public interface ReviewService {
    void addReview(Review review);

    List<Review> getReviewsByProductId(long productId);

    List<ProductRating> getAverageRatingsGroupByProductId();

    ProductRating getAverageRatingForProduct(long productId);

    void editReview(long reviewId, Review updatedReview);

    void deleteReview(long reviewId);
}
