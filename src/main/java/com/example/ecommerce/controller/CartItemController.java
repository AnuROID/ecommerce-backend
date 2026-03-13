package com.example.ecommerce.controller;

import com.example.ecommerce.exception.CartItemException;
import com.example.ecommerce.exception.UserException;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.User;
import com.example.ecommerce.request.AddItemRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.CartItemService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    // Add or create a cart item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCartItem(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest req
    ) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.isCartItemExist(null, null, req.getSize(), user.getId()); // You can implement proper logic in service

        ApiResponse response = new ApiResponse();
        response.setMessage("Cart item processed successfully");
        response.setStatus(true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update a cart item
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem
    ) throws CartItemException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    // Remove a cart item
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<ApiResponse> removeCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId
    ) throws CartItemException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMessage("Cart item removed successfully");
        response.setStatus(true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a cart item by id
    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId
    ) throws CartItemException, UserException {

        userService.findUserProfileByJwt(jwt); // just to validate user
        CartItem cartItem = cartItemService.findCartItemById(cartItemId);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
}
