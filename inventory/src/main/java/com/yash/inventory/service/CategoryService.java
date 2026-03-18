package com.yash.inventory.service;

import com.yash.inventory.dto.CategoryRequest;
import com.yash.inventory.entity.Category;
import com.yash.inventory.entity.Company;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CategoryRepository;
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

    public String createCategory(CategoryRequest request) {

        Long companyId = SecurityUtils.getCurrentCompanyId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .company(company) // link to logged-in company
                .build();

        categoryRepository.save(category);

        return "Category created successfully";
    }

    public List<Category> getAllCategories() {

        Long companyId = SecurityUtils.getCurrentCompanyId();
        return categoryRepository.findByCompanyId(companyId); // company-scoped fetch
    }
}