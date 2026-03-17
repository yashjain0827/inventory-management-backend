package com.yash.inventory.controller;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.OrderRequest;
import com.yash.inventory.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<String> createOrder(@Valid @RequestBody OrderRequest request) {

        String message = orderService.createOrder(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }
}