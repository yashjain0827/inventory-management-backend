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

    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        return ApiResponse.<Product>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(product)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateProduct(@PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        String message = productService.updateProduct(id, request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {

        String message = productService.deleteProduct(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }
}