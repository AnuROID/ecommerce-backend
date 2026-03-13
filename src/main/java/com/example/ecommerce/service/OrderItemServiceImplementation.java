package com.example.ecommerce.service;

import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImplementation implements OrderItemService{

    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return  orderItemRepository.save(orderItem);
    }
}
