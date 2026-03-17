package com.yash.inventory.service;

import com.yash.inventory.dto.OrderRequest;
import com.yash.inventory.entity.*;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.OrderRepository;
import com.yash.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public String createOrder(OrderRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        OrderType type = OrderType.valueOf(request.getType());

        // 🔥 CORE LOGIC
        if (type == OrderType.PURCHASE) {
            product.setQuantity(product.getQuantity() + request.getQuantity());
        } else {
            if (product.getQuantity() < request.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }
            product.setQuantity(product.getQuantity() - request.getQuantity());
        }

        productRepository.save(product);

        Order order = Order.builder()
                .product(product)
                .quantity(request.getQuantity())
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        return "Order processed successfully";
    }
}