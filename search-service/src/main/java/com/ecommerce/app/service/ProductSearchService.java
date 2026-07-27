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

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.json.JsonData;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

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
}