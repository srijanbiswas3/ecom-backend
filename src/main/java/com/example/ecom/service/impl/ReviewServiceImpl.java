package com.example.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.entity.Review;
import com.example.ecom.exception.ReviewNotFoundException;
import com.example.ecom.repository.ReviewRepository;
import com.example.ecom.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProductId(long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Object[]> getAverageRatingsGroupByProductId() {
        return reviewRepository.findAverageRatingsGroupByProductId();
    }

    @Override
    public double getAverageRatingForProduct(long productId) {
        Double averageRating = reviewRepository.findAverageRatingByProductId(productId);
        return averageRating != null ? averageRating : 0.0;
    }

    @Override
    public void editReview(long reviewId, Review updatedReview) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));
        existingReview.setRating(updatedReview.getRating());
        existingReview.setComment(updatedReview.getComment());
        reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));
        reviewRepository.delete(existingReview);
    }
}
