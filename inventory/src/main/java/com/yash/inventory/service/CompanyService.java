package com.yash.inventory.service;

import com.yash.inventory.dto.CompanyRequest;
import com.yash.inventory.entity.Company;
import com.yash.inventory.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public String createCompany(CompanyRequest request) {

        Company company = Company.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        companyRepository.save(company);

        return "Company created successfully";
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}