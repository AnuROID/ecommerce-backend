package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.RatingRepository;
import com.example.ecommerce.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class RatingServiceImplementation implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        // Optional: Prevent duplicate ratings
        // check if this usr already rated this product
        Rating existing = ratingRepository.findByUserAndProduct(user.getId(),product.getId());
        if (existing != null) {
            existing.setRating(req.getRating());
            existing.setCreateAt(LocalDateTime.now());
            return ratingRepository.save(existing);
        }

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreateAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
