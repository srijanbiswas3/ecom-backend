package com.example.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecom.dto.ProductRating;
import com.example.ecom.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(long productId);

    @Query("SELECT new com.example.ecom.dto.ProductRating( r.product.id, AVG(r.rating),COUNT(r.user)) FROM Review r GROUP BY r.product.id")
    List<ProductRating> findAverageRatingsGroupByProductId();

    @Query("SELECT new com.example.ecom.dto.ProductRating( r.product.id,AVG(r.rating),count(r.user)) FROM Review r WHERE r.product.id = :productId GROUP BY r.product.id")
    ProductRating findAverageRatingByProductId(long productId);

    @Query("SELECT new com.example.ecom.dto.ProductRating( r.product.id,AVG(r.rating),count(r.user)) FROM Review r WHERE r.product.id = :productId GROUP BY r.product.id, r.rating")
    List<ProductRating> findProductRatingList(long productId);
}