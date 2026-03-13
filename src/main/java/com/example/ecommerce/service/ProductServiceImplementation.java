package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public ProductServiceImplementation(ProductRepository productRepository,
                                        UserService userService,
                                        CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        // Top-level category
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        if (topLevel == null) {
            topLevel = new Category();
            topLevel.setName(req.getTopLevelCategory());
            topLevel.setLevel(1);
            topLevel = categoryRepository.save(topLevel);
        }

        // Second-level category
        Category secondLevel = categoryRepository.findByNameAndParentCategory(req.getSecondLevelCategory(), topLevel);
        if (secondLevel == null) {
            secondLevel = new Category();
            secondLevel.setName(req.getSecondLevelCategory());
            secondLevel.setParentCategory(topLevel);
            secondLevel.setLevel(2);
            secondLevel = categoryRepository.save(secondLevel);
        }

        // Third-level category
        Category thirdLevel = categoryRepository.findByNameAndParentCategory(req.getThirdLevelCategory(), secondLevel);
        if (thirdLevel == null) {
            thirdLevel = new Category();
            thirdLevel.setName(req.getThirdLevelCategory());
            thirdLevel.setParentCategory(secondLevel);
            thirdLevel.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLevel);
        }

        // Create product
        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setQuantity(req.getQuantity());
        product.setImageUrl(req.getImageUrl());
        product.setSizes(req.getSizes());
        product.setTopLevelCategory(topLevel);
        product.setSecondLevelCategory(secondLevel);
        product.setThirdLevelCategory(thirdLevel);

        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if (req.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id " + id));
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return productRepository.findByTopLevelCategory_Name(category);
    }

    @Override
    public Page<Product> getAllProduct(String category,
                                       List<String> colors,
                                       List<String> sizes,
                                       Integer minPrice,
                                       Integer maxPrice,
                                       Integer minDiscount,
                                       String sort,
                                       String stock,
                                       Integer pageNumber,
                                       Integer pageSize) {
        // --- Normalize category first ---
        String topLevel = null, secondLevel = null, thirdLevel = null;
        if (category != null && !category.isEmpty()) {
            category = category.replace("-", "_").toLowerCase(); // normalize slug
            String[] parts = category.split("/");
            if (parts.length > 0) topLevel = parts[0];
            if (parts.length > 1) secondLevel = parts[1];
            if (parts.length > 2) thirdLevel = parts[2];
        }

        // Sorting
        Sort sortOption = Sort.unsorted();
        if ("price_low_to_high".equalsIgnoreCase(sort)) {
            sortOption = Sort.by(Sort.Direction.ASC, "discountedPrice");
        } else if ("price_high_to_low".equalsIgnoreCase(sort)) {
            sortOption = Sort.by(Sort.Direction.DESC, "discountedPrice");
        } else if ("newest_first".equalsIgnoreCase(sort)) {
            sortOption = Sort.by(Sort.Direction.DESC, "createdAt");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);

        // Fix sizes empty → null
        if (sizes != null && sizes.isEmpty()) {
            sizes = null;
        }
        System.out.println("📌 Filter Params:");
        System.out.println("topLevel=" + topLevel);
        System.out.println("secondLevel=" + secondLevel);
        System.out.println("thirdLevel=" + thirdLevel);
        System.out.println("minPrice=" + minPrice);
        System.out.println("maxPrice=" + maxPrice);
        System.out.println("minDiscount=" + minDiscount);
        System.out.println("stock=" + stock);
        System.out.println("color=" + (colors != null && !colors.isEmpty() ? colors.get(0) : null));
        System.out.println("sizes=" + sizes);


        // Fetch directly from repository
        Page<Product> page = productRepository.filterProducts(
                topLevel,
                secondLevel,
                thirdLevel,
                minPrice,
                maxPrice,
                minDiscount,
                stock,
                (colors != null && !colors.isEmpty() ? colors.get(0) : null), // NOTE: supports only 1 color now
                sizes,
                pageable
        );


        // ✅ Return directly (no double filtering)
        return page;
    }
}
