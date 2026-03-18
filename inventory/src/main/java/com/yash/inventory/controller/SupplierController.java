package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.SupplierRequest;
import com.yash.inventory.entity.Supplier;
import com.yash.inventory.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ApiResponse<String> createSupplier(@Valid @RequestBody SupplierRequest request) {
        String message = supplierService.createSupplier(request);
        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ApiResponse.<List<Supplier>>builder()
                .success(true)
                .message("Suppliers fetched successfully")
                .data(suppliers)
                .build();
    }
}