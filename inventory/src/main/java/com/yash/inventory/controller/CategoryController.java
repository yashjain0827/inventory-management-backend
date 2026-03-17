package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.CategoryRequest;
import com.yash.inventory.entity.Category;
import com.yash.inventory.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<String> createCategory(@Valid @RequestBody CategoryRequest request) {

        String message = categoryService.createCategory(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {

        List<Category> categories = categoryService.getAllCategories();

        return ApiResponse.<List<Category>>builder()
                .success(true)
                .message("Categories fetched successfully")
                .data(categories)
                .build();
    }
}