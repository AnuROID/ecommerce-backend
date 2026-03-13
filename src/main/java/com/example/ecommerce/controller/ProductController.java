package com.example.ecommerce.controller;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
      Get product by ID
     */
    @GetMapping("/product/id/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException {
//        Product product = productService.findProductById(productId);
        Product product=productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

//    /**
//     Get products by category
//     */
//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<Product>> findProductsByCategory(@PathVariable String category) {
//        List<Product> products = productService.findProductByCategory(category);
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

    /**
      Get all products with filters (pagination + sorting + filtering)
     Example: /api/products/all?category=men&colors=red,blue&minPrice=500&maxPrice=2000&pageNumber=0&pageSize=10
     */
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductsByCategoryHandler(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "") List<String> colors,
            @RequestParam(required = false, defaultValue = "") List<String> sizes,
            @RequestParam(required = false, defaultValue = "0") Integer minPrice,
            @RequestParam(required = false, defaultValue = "100000") Integer maxPrice,
            @RequestParam(required = false, defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false, defaultValue = "price_low") String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<Product> products = productService.getAllProduct(
                category, colors, sizes, minPrice, maxPrice, minDiscount,
                sort, stock, pageNumber, pageSize
        );
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
