package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.User;
import com.example.ecommerce.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);

}
