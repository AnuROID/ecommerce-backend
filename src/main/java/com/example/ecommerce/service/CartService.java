package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart creatCart(User user);
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
    public  Cart findUserCart(Long userId);
}
