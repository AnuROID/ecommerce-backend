package com.example.ecommerce.controller;

import com.example.ecommerce.exception.OrderException;
import com.example.ecommerce.exception.UserException;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
      Place/Create an Order
     */
    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user,shippingAddress);
        System.out.println("order"+order);
        System.out.println("order"+order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
      Get Order by ID
     */
    @GetMapping("/{Id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("Id") Long orderId,
                                              @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user=userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     Get All Orders of User
     */
    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
      Cancel/Delete an Order
     */
    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId,
                                                   @RequestHeader("Authorization") String jwt) throws OrderException {
        orderService.deleteOrder(orderId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Order deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
