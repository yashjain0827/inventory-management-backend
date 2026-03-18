package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.InventoryRequest;
import com.yash.inventory.entity.Inventory;
import com.yash.inventory.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ApiResponse<String> addInventory(@Valid @RequestBody InventoryRequest request) {

        String message = inventoryService.addInventory(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Inventory>> getInventory() {

        List<Inventory> inventoryList = inventoryService.getInventory();

        return ApiResponse.<List<Inventory>>builder()
                .success(true)
                .message("Inventory fetched successfully")
                .data(inventoryList)
                .build();
    }
}