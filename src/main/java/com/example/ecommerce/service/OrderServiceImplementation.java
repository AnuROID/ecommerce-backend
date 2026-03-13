package com.example.ecommerce.service;

import com.example.ecommerce.exception.OrderException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImplementation(UserRepository userRepository,
                                      OrderItemService orderItemService,
                                      CartService cartService,
                                      OrderRepository orderRepository,
                                      AddressRepository addressRepository,
                                      OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        Address savedAddress=addressRepository.save(shippingAddress);
        Cart cart = cartService.findUserCart(user.getId());

        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot create order.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setOrderStatus("CREATED");

        // Copy items from cart to order
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setOrder(order); // set parent order
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);




        return orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with id: " + orderId));
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepository.getUsersOrders(userId);
    }


    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
        return orderRepository.findAll();
    }
}
