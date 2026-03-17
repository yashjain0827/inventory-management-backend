package com.yash.inventory.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yash.inventory.dto.OrderRequest;
import com.yash.inventory.entity.Inventory;
import com.yash.inventory.entity.Order;
import com.yash.inventory.entity.OrderType;
import com.yash.inventory.entity.Product;
import com.yash.inventory.entity.Warehouse;
import com.yash.inventory.exception.InsufficientStockException;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.InventoryRepository;
import com.yash.inventory.repository.OrderRepository;
import com.yash.inventory.repository.ProductRepository;
import com.yash.inventory.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional
    public String createOrder(OrderRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        Inventory inventory = inventoryRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElse(Inventory.builder()
                        .product(product)
                        .warehouse(warehouse)
                        .quantity(0)
                        .build());

        OrderType type = OrderType.valueOf(request.getType());

        if (type == OrderType.PURCHASE) {
            inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        } else {
            if (inventory.getQuantity() < request.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock in this warehouse");
            }
            inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
        }

        inventoryRepository.save(inventory);

        Order order = Order.builder()
                .product(product)
                .warehouse(warehouse)
                .quantity(request.getQuantity())
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        return "Order processed successfully";
    }
}