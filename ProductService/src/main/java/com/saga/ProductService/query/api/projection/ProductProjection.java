package com.saga.ProductService.query.api.projection;

import com.saga.ProductService.command.api.data.Product;
import com.saga.ProductService.command.api.data.ProductRepository;
import com.saga.ProductService.command.api.model.ProductRestModel;
import com.saga.ProductService.query.api.queries.GetProductsQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery) {
        List<Product> productList = productRepository.findAll();

        List<ProductRestModel> productRestModels = productList.stream()
                .map(product ->
                    ProductRestModel
                            .builder()
                            .name(product.getName())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .build())
                .collect(Collectors.toList());

        return productRestModels;
    }
}
