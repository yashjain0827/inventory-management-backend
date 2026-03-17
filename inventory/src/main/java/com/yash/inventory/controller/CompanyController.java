package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.CompanyRequest;
import com.yash.inventory.entity.Company;
import com.yash.inventory.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ApiResponse<String> createCompany(@Valid @RequestBody CompanyRequest request) {

        String message = companyService.createCompany(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Company>> getAllCompanies() {

        List<Company> companies = companyService.getAllCompanies();

        return ApiResponse.<List<Company>>builder()
                .success(true)
                .message("Companies fetched successfully")
                .data(companies)
                .build();
    }
}