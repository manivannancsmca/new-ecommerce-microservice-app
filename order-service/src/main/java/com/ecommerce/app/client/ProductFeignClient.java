package com.ecommerce.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.app.config.CustomFeignErrorDecoder;
import com.ecommerce.app.dto.ProductResponse;

@FeignClient(
    name = "product-catalog-service", // Service name in Eureka/Consul
    path = "/api/v1/products",    // Base mapping path of ProductController (adjust if needed)
    configuration = CustomFeignErrorDecoder.class
)
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    ProductResponse getProductById(@PathVariable("productId") long productId);
}
