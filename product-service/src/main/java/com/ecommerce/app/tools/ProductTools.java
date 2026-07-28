package com.ecommerce.app.tools;

import com.ecommerce.app.dto.ProductResponse;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductTools {

    private final ProductService productService;

    @McpTool(
            name = "create_or_update_product",
            description = "Creates a new product or updates an existing product details in the catalog."
    )
    public ProductResponse createOrUpdateProduct(
            @McpToolParam(description = "Product object containing details such as ID, name, price, description, and stock quantity", required = true)
            Product request) {
        return productService.createOrUpdateProduct(request);
    }

    @McpTool(
            name = "get_product_by_id",
            description = "Fetches complete product details using its unique product ID."
    )
    public ProductResponse getProductById(
            @McpToolParam(description = "The unique numeric ID of the product to retrieve", required = true)
            long productId) {
        return productService.getProductById(productId);
    }

}
