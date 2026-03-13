package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private int price;

    @Column(name="discounted_price")
    private int discountedPrice;

    @Column(name="discount_percent")
    private int discountPercent;

    @Column(name="quantity")
    private int quantity;

    @Column(name="brand")
    private String brand;

    @Column(name="color")
    private String color;

    @ElementCollection
    @CollectionTable(
            name = "product_sizes",
            joinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Sizes> sizes = new HashSet<>();

    @Column(name="image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "num_ratings")
    private int numRatings;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;

    // 🟢 Add three separate category levels
    @ManyToOne
    @JoinColumn(name = "top_level_category_id")
    private Category topLevelCategory;

    @ManyToOne
    @JoinColumn(name = "second_level_category_id")
    private Category secondLevelCategory;

    @ManyToOne
    @JoinColumn(name = "third_level_category_id")
    private Category thirdLevelCategory;

    private LocalDateTime createdAt;

    public Product() {}

    // Getters & setters here...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

//    public int getDiscountPercent() {
//        return discountPercent;
//    }
    public int getDiscountPercent(){
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(Set<Sizes> sizes) {
        this.sizes = sizes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }

    // categories
    public Category getTopLevelCategory() { return topLevelCategory; }
    public void setTopLevelCategory(Category topLevelCategory) { this.topLevelCategory = topLevelCategory; }

    public Category getSecondLevelCategory() { return secondLevelCategory; }
    public void setSecondLevelCategory(Category secondLevelCategory) { this.secondLevelCategory = secondLevelCategory; }

    public Category getThirdLevelCategory() { return thirdLevelCategory; }
    public void setThirdLevelCategory(Category thirdLevelCategory) { this.thirdLevelCategory = thirdLevelCategory; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
