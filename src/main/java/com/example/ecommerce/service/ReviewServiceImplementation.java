package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReviewServiceImplementation implements ReviewService {
    private  ReviewRepository reviewRepository;
    private  ProductService productService;
    private ProductRepository productRepository;


    public ReviewServiceImplementation(ReviewRepository reviewRepository,ProductRepository productRepository,ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository=productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        // ✅ Fetch product by ID
        Product product = productService.findProductById(req.getProductId());

        // ✅ Create Review object
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(req.getReview());   // review text/comment
        review.setCreatedAt(LocalDateTime.now());

        // ✅ Save in database
        return reviewRepository.save(review);

    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
