package com.ecommerce.app.tools;

import com.ecommerce.app.dto.ProductResponse;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductTools {

    private final ProductService productService;

    @Tool(
            name = "create_or_update_product",
            description = "Creates a new product or updates an existing product details in the catalog."
    )
    public ProductResponse createOrUpdateProduct(
            @ToolParam(description = "Product details including id (if updating), name, price, description, and stock quantity", required = true)
            Product product) { // DTO recommended
        return productService.createOrUpdateProduct(product);
    }

    @Tool(
            name = "get_product_by_id",
            description = "Fetches complete product details using its unique product ID."
    )
    public ProductResponse getProductById(
            @ToolParam(description = "The unique numeric ID of the product to retrieve", required = true)
            long productId) {
        return productService.getProductById(productId);
    }
}