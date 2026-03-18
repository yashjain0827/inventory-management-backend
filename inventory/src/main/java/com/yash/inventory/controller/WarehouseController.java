package com.yash.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.WarehouseRequest;
import com.yash.inventory.entity.Warehouse;
import com.yash.inventory.service.WarehouseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    @GetMapping
    public ApiResponse<List<Warehouse>> getWarehouses() {

        List<Warehouse> warehouses = warehouseService.getWarehouses();

        return ApiResponse.<List<Warehouse>>builder()
                .success(true)
                .message("Warehouses fetched successfully")
                .data(warehouses)
                .build();
    }
}