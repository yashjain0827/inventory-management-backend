package com.yash.inventory.service;

import com.yash.inventory.dto.WarehouseRequest;
import com.yash.inventory.entity.Company;
import com.yash.inventory.entity.Warehouse;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final CompanyRepository companyRepository;

    public String createWarehouse(WarehouseRequest request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Warehouse warehouse = Warehouse.builder()
                .name(request.getName())
                .location(request.getLocation())
                .company(company)
                .build();

        warehouseRepository.save(warehouse);

        return "Warehouse created successfully";
    }

    public List<Warehouse> getWarehousesByCompany(Long companyId) {
        return warehouseRepository.findByCompanyId(companyId);
    }
}