package com.yash.inventory.service;

import com.yash.inventory.dto.CategoryRequest;
import com.yash.inventory.entity.Category;
import com.yash.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public String createCategory(CategoryRequest request) {

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        categoryRepository.save(category);

        return "Category created successfully";
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}