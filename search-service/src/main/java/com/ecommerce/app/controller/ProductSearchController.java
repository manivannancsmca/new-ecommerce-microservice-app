package com.ecommerce.app.controller;

// ProductSearchController.java
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.document.ProductDocument;
import com.ecommerce.app.dto.ProductSearchRequest;
import com.ecommerce.app.dto.ProductSearchResponse;
import com.ecommerce.app.service.ProductSearchService;

@RestController
@RequestMapping("/api/v1/search/products")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService searchService;

    @GetMapping
    public ResponseEntity<ProductSearchResponse> searchProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ProductSearchRequest request = new ProductSearchRequest();
        request.setQuery(query);
        request.setCategoryId(categoryId);
        if (minPrice != null)
            request.setMinPrice(java.math.BigDecimal.valueOf(minPrice));
        if (maxPrice != null)
            request.setMaxPrice(java.math.BigDecimal.valueOf(maxPrice));
        request.setPage(page);
        request.setSize(size);

        return ResponseEntity.ok(searchService.search(request));
    }

    @GetMapping("/by-name")
    public ResponseEntity<ProductSearchResponse> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ProductSearchResponse response = searchService.searchByName(name, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDocument> getProductById(@PathVariable Long productId) {
        ProductDocument productDocument = searchService.searchByProductId(productId);
        return ResponseEntity.ok(productDocument);
    }
}
