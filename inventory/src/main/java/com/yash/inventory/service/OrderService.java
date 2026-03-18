package com.yash.inventory.service;

import java.time.LocalDateTime;
import java.util.List;

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
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.repository.InventoryRepository;
import com.yash.inventory.repository.OrderRepository;
import com.yash.inventory.repository.ProductRepository;
import com.yash.inventory.repository.WarehouseRepository;
import com.yash.inventory.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public String createOrder(OrderRequest request) {

        Long companyId = SecurityUtils.getCurrentCompanyId();

        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        // ✅ SECURITY CHECK (VERY IMPORTANT)
        if (!warehouse.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Unauthorized warehouse access");
        }

        // ✅ STRICT INVENTORY FETCH (NO AUTO CREATE)
        Inventory inventory = inventoryRepository
                .findByProductIdAndWarehouseIdAndCompanyId(
                        product.getId(),
                        warehouse.getId(),
                        companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found for this warehouse & product"));

        OrderType type = OrderType.valueOf(request.getType());

        if (type == OrderType.PURCHASE) {
            inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        } else {
            if (inventory.getQuantity() < request.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock");
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
                .company(company)
                .build();

        orderRepository.save(order);

        return "Order processed successfully";
    }

    public List<Order> getOrders() {
        Long companyId = SecurityUtils.getCurrentCompanyId();
        return orderRepository.findByCompanyId(companyId);
    }
}