package com.example.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecom.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(long productId);

    @Query("SELECT r.product.id, AVG(r.rating) FROM Review r GROUP BY r.product.id")
    List<Object[]> findAverageRatingsGroupByProductId();

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double findAverageRatingByProductId(long productId);

}