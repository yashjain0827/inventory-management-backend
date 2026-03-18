package com.yash.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.inventory.dto.ApiResponse;
import com.yash.inventory.dto.OrderRequest;
import com.yash.inventory.entity.Order;
import com.yash.inventory.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    @GetMapping
    public ApiResponse<List<Order>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ApiResponse.<List<Order>>builder()
                .success(true)
                .message("Orders fetched successfully")
                .data(orders)
                .build();
    }

}