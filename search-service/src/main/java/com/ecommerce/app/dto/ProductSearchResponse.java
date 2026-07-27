package com.ecommerce.app.dto;

import java.util.List;

import com.ecommerce.app.document.ProductDocument;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSearchResponse {
    private List<ProductDocument> products;
    private long totalHits;
    private int page;
    private int size;
}
