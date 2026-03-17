package com.yash.inventory.service;

import com.yash.inventory.dto.SupplierRequest;
import com.yash.inventory.entity.Supplier;
import com.yash.inventory.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public String createSupplier(SupplierRequest request) {

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        supplierRepository.save(supplier);

        return "Supplier created successfully";
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
}