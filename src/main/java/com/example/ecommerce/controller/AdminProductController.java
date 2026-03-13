package com.example.ecommerce.controller;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.request.CreateProductRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) throws ProductException {
        productService.deleteProduct(productId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Product deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // ✅ Get all products with filters & pagination
//    @GetMapping("/all") public ResponseEntity<List<Product>>
//    findAllProduct() { List<Product> products = productService.findAllProducts();
//    return new ResponseEntity<>(products, HttpStatus.OK); }
    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "") List<String> colors,
            @RequestParam(required = false, defaultValue = "") List<String> sizes,
            @RequestParam(required = false, defaultValue = "0") Integer minPrice,
            @RequestParam(required = false, defaultValue = "1000000") Integer maxPrice,
            @RequestParam(required = false, defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false, defaultValue = "price_low") String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<Product> products = productService.getAllProduct(
                category,
                colors != null ? colors : Collections.emptyList(),
                sizes != null ? sizes : Collections.emptyList(),
                minPrice,
                maxPrice,
                minDiscount,
                sort,
                stock,
                pageNumber,
                pageSize
        );

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req,
                                                 @PathVariable("productId") Long productId) throws ProductException {
        Product product = productService.updateProduct(productId, req);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
//optional for add bulk product creation
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){
        for(CreateProductRequest product:req) {
            productService.createProduct(product);
        }
            ApiResponse res=new ApiResponse();
            res.setMessage("product created successfully");
            res.setStatus(true);

            return  new ResponseEntity<>(res,HttpStatus.CREATED);

    }

}
