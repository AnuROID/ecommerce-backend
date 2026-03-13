package com.example.ecommerce.service;

import com.example.ecommerce.exception.OrderException;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
     public Order createOrder(User user, Address shippingAddress);
     public Order findOrderById(Long orderId) throws OrderException;
     public List<Order> userOrderHistory(Long userId);
    public Order placeOrder(Long orderId) throws OrderException;
    public Order confirmOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;

    List<Order> getAllOrders();
    List<Order> deleteOrder(Long orderId) throws OrderException;
}
