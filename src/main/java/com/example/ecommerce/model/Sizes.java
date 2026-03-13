package com.example.ecommerce.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Sizes {

    private String size;

    public Sizes() {}

    public Sizes(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
