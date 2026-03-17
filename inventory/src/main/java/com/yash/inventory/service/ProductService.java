package com.yash.inventory.service;

import com.yash.inventory.dto.ProductRequest;
import com.yash.inventory.entity.Product;
import com.yash.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String createProduct(ProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        productRepository.save(product);

        return "Product created successfully";
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}