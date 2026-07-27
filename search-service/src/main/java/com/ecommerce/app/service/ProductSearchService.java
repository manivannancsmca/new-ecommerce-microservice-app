package com.ecommerce.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ecommerce.app.document.ProductDocument;
import com.ecommerce.app.dto.ProductSearchRequest;
import com.ecommerce.app.dto.ProductSearchResponse;
import com.ecommerce.app.exception.ProductNotFoundException;
import com.ecommerce.app.repository.ProductSearchRepository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.json.JsonData;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    private final ProductSearchRepository searchRepository;

    public ProductSearchResponse search(ProductSearchRequest request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());

        var boolQuery = new BoolQuery.Builder();

        // Active filter
        boolQuery.must(m -> m.term(t -> t.field("active").value(true)));

        // Full-text fuzzy search on 'name'
        if (StringUtils.hasText(request.getQuery())) {
            boolQuery.must(m -> m.match(k -> k.field("name").query(request.getQuery()).fuzziness("AUTO")));
        }

        // Exact match on Category
        if (request.getCategoryId() != null) {
            boolQuery.must(m -> m.term(t -> t.field("categoryId").value(request.getCategoryId())));
        }

        // Range query on Price
        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            boolQuery.must(m -> m.range(r -> r.number(n -> {
                n.field("price");
                if (request.getMinPrice() != null) {
                    n.gte(request.getMinPrice().doubleValue());
                }
                if (request.getMaxPrice() != null) {
                    n.lte(request.getMaxPrice().doubleValue());
                }
                return n;
            })));
        }

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(boolQuery.build()._toQuery())
                .withPageable(pageable)
                .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(searchQuery, ProductDocument.class);

        List<ProductDocument> products = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return ProductSearchResponse.builder()
                .products(products)
                .totalHits(hits.getTotalHits())
                .page(request.getPage())
                .size(request.getSize())
                .build();
    }

    public ProductSearchResponse searchByName(String name, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        var boolQuery = new BoolQuery.Builder();

        // 1. Filter only active products
        boolQuery.must(m -> m.term(t -> t.field("active").value(true)));

        // 2. Perform fuzzy full-text search strictly on the 'name' field
        if (name != null && !name.trim().isEmpty()) {
            boolQuery.must(m -> m.match(k -> k
                    .field("name")
                    .query(name.trim())
                    .fuzziness("AUTO") // Allows minor typos (e.g., 'iphne' matches 'iphone')
            ));
        }

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(boolQuery.build()._toQuery())
                .withPageable(pageable)
                .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(searchQuery, ProductDocument.class);

        List<ProductDocument> products = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return ProductSearchResponse.builder()
                .products(products)
                .totalHits(hits.getTotalHits())
                .page(page)
                .size(size)
                .build();

    }

    public ProductDocument searchByProductId(Long productId) {
        String docId = String.valueOf(productId);

        return searchRepository.findById(docId)
                .filter(ProductDocument::getActive)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}