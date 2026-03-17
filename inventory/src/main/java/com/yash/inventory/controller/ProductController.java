package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.ProductRequest;
import com.yash.inventory.entity.Product;
import com.yash.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<String> createProduct(@Valid @RequestBody ProductRequest request) {

        String message = productService.createProduct(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Product>> getAllProducts() {

        List<Product> products = productService.getAllProducts();

        return ApiResponse.<List<Product>>builder()
                .success(true)
                .message("Products fetched successfully")
                .data(products)
                .build();
    }
}