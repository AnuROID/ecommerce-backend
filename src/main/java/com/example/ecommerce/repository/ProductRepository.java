package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Query("SELECT p FROM Product p " +
//            "WHERE (:category = '' OR p.category.name = :category) " +
//            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
//            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
//            "ORDER BY " +
//            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
//            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
//    public List<Product> filterProducts(
//            @Param("category") String category,
//            @Param("minPrice") Integer minPrice,
//            @Param("maxPrice") Integer maxPrice,
//            @Param("minDiscount") Integer minDiscount,
//            @Param("sort") String sort);
// find by category name
List<Product> findByTopLevelCategory_Name(String category);

    // custom filtering (implement using @Query or QueryDSL/Criteria API)
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.sizes s " +
            "WHERE (:topLevel IS NULL OR LOWER(p.topLevelCategory.name) = LOWER(:topLevel)) " +
            "AND (:secondLevel IS NULL OR LOWER(p.secondLevelCategory.name) = LOWER(:secondLevel)) " +
            "AND (:thirdLevel IS NULL OR LOWER(p.thirdLevelCategory.name) = LOWER(:thirdLevel)) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
            "AND (:stock IS NULL OR " +
            "     (:stock = 'in_stock' AND p.quantity > 0) OR " +
            "     (:stock = 'out_of_stock' AND p.quantity < 1)) " +
            "AND (:color IS NULL OR LOWER(p.color) = LOWER(:color)) " +
            "AND (:sizes IS NULL OR LOWER(s.size) IN :sizes)")   // ✅ FIXED
    Page<Product> filterProducts(
            @Param("topLevel") String topLevel,
            @Param("secondLevel") String secondLevel,
            @Param("thirdLevel") String thirdLevel,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minDiscount") Integer minDiscount,
            @Param("stock") String stock,
            @Param("color") String color,
            @Param("sizes") List<String> sizes,
            Pageable pageable);
}

