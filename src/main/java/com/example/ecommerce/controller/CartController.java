package com.example.ecommerce.controller;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.exception.UserException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.request.AddItemRequest;
import com.example.ecommerce.request.CreateProductRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")

public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Cart cart=cartService.findUserCart(user.getId());

        return  new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/create-multiple")
    public ResponseEntity<ApiResponse> createMultipleProducts(@RequestBody CreateProductRequest[] req) {
        for (CreateProductRequest productReq : req) {
            productService.createProduct(productReq);
        }

        ApiResponse res = new ApiResponse();
        res.setMessage(" products created successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



}
