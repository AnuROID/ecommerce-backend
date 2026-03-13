package com.example.ecommerce.controller;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.exception.UserException;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.request.ReviewRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.ReviewService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    /**
     *  Add a review for a product
     */
    @PostMapping("/create")
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest req,
                                            @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    /**
      Get all reviews for a product
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId) throws ProductException {
        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     Delete a review by reviewId (for admin or review owner)
     */
//    @DeleteMapping("/{reviewId}/delete")
//    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
//                                                    @RequestHeader("Authorization") String jwt) throws UserException {
//        User user = userService.findUserProfileByJwt(jwt);
//        reviewService.deleteReview(reviewId, user.getId());
//
//        ApiResponse res = new ApiResponse();
//        res.setMessage("Review deleted successfully");
//        res.setStatus(true);
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
}
