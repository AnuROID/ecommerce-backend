package com.example.ecommerce.controller;

import com.example.ecommerce.exception.OrderException;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED); // FIXED missing ;
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.confirmOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,
                                                    @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException {
        orderService.deleteOrder(orderId);
        ApiResponse res=new ApiResponse();
        res.setMessage("Order deleted succesfully");
        res.setStatus(true);
        return  new ResponseEntity<>(res,HttpStatus.OK);
    }

}
