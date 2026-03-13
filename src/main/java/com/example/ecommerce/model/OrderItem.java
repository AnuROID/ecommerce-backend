package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Each order item belongs to ONE order
    @JsonIgnore
    @ManyToOne
    private Order order;

    // Each order item is linked to ONE product
    @ManyToOne
    private Product product;

    private String size;
    private int quantity;
    private double price;             // original price
    private double discountedPrice;   // price after discount
    private Long userId;
    private LocalDateTime deliveryDate;
    public OrderItem(){

    }

    public OrderItem(Long id, Order order, Product product, String size, int quantity,
                     double price, double discountedPrice, Long userId, LocalDateTime deliveryDate) {
        super();
        this.id = id;
        this.order = order;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.userId = userId;
        this.deliveryDate = deliveryDate;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
