package com.example.ecommerce.repository;

import com.example.ecommerce.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // ✅ Get all ratings for a product
    @Query("SELECT r FROM Rating r WHERE r.product.id = :productId")
    List<Rating> getAllProductsRating(@Param("productId") Long productId);

    // ✅ Find if a specific user has already rated a specific product
    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.product.id = :productId")
    Rating findByUserAndProduct(@Param("userId") Long userId,
                                @Param("productId") Long productId);
}
