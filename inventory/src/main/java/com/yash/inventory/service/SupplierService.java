package com.yash.inventory.service;

import com.yash.inventory.dto.SupplierRequest;
import com.yash.inventory.entity.Company;
import com.yash.inventory.entity.Supplier;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.repository.SupplierRepository;
import com.yash.inventory.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final CompanyRepository companyRepository;

    public String createSupplier(SupplierRequest request) {

        Long companyId = SecurityUtils.getCurrentCompanyId();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .company(company) // link supplier to company
                .build();

        supplierRepository.save(supplier);

        return "Supplier created successfully";
    }

    public List<Supplier> getAllSuppliers() {

        Long companyId = SecurityUtils.getCurrentCompanyId();

        return supplierRepository.findByCompanyId(companyId); // company-scoped fetch
    }
}