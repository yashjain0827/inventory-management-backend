package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.WarehouseRequest;
import com.yash.inventory.entity.Warehouse;
import com.yash.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ApiResponse<String> createWarehouse(@Valid @RequestBody WarehouseRequest request) {

        String message = warehouseService.createWarehouse(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @GetMapping("/company/{companyId}")
    public ApiResponse<List<Warehouse>> getWarehouses(@PathVariable Long companyId) {

        List<Warehouse> warehouses = warehouseService.getWarehousesByCompany(companyId);

        return ApiResponse.<List<Warehouse>>builder()
                .success(true)
                .message("Warehouses fetched successfully")
                .data(warehouses)
                .build();
    }
}