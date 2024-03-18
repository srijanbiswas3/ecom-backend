package com.example.ecom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.dto.ProductRating;
import com.example.ecom.entity.Review;
import com.example.ecom.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public void addReview(@RequestBody Review review) {
        reviewService.addReview(review);
    }

    @GetMapping("/{productId}")
    public List<Review> getReviewsByProductId(@PathVariable long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @GetMapping("/average/{productId}")
    public ProductRating getAverageRating(@PathVariable long productId) {
        return reviewService.getAverageRatingForProduct(productId);
    }

    @GetMapping("/average")
    public Map<Long, ProductRating> getAverageRatingsGroupByProductId() {
        List<ProductRating> results = reviewService.getAverageRatingsGroupByProductId();
        Map<Long, ProductRating> averageRatings = new HashMap<>();

        for (ProductRating result : results) {
            ProductRating productRating = new ProductRating(result.getAvgRating(), result.getUserCount());
            averageRatings.put(result.getId(), productRating);
        }

        return averageRatings;
    }

    @PutMapping("/{reviewId}")
    public void editReview(@PathVariable long reviewId, @RequestBody Review updatedReview) {
        reviewService.editReview(reviewId, updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
