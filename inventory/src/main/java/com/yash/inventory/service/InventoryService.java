package com.yash.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yash.inventory.dto.InventoryRequest;
import com.yash.inventory.entity.Company;
import com.yash.inventory.entity.Inventory;
import com.yash.inventory.entity.Product;
import com.yash.inventory.entity.Warehouse;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.repository.InventoryRepository;
import com.yash.inventory.repository.ProductRepository;
import com.yash.inventory.repository.WarehouseRepository;
import com.yash.inventory.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

        private final InventoryRepository inventoryRepository;
        private final ProductRepository productRepository;
        private final WarehouseRepository warehouseRepository;
        private final CompanyRepository companyRepository;

        public class UnauthorizedAccessException extends RuntimeException {
                public UnauthorizedAccessException(String message) {
                        super(message);
                }
        }

        // Create or update inventory
        public String addInventory(InventoryRequest request) {

                Long companyId = SecurityUtils.getCurrentCompanyId();

                Company company = companyRepository.findById(companyId)
                                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

                Product product = productRepository.findById(request.getProductId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                if (!product.getCompany().getId().equals(companyId)) {
                        throw new UnauthorizedAccessException("Unauthorized product access");
                }

                Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

                if (!warehouse.getCompany().getId().equals(companyId)) {
                        throw new UnauthorizedAccessException("Unauthorized warehouse access");
                }

                Inventory inventory = inventoryRepository
                                .findByProductIdAndWarehouseIdAndCompanyId(
                                                product.getId(),
                                                warehouse.getId(),
                                                companyId)
                                .orElse(Inventory.builder()
                                                .product(product)
                                                .warehouse(warehouse)
                                                .company(company)
                                                .quantity(0)
                                                .build());

                inventory.setQuantity(request.getQuantity());

                inventoryRepository.save(inventory);

                return "Inventory saved successfully";
        }

        // Fetch all inventory for logged-in company
        public List<Inventory> getInventory() {
                Long companyId = SecurityUtils.getCurrentCompanyId();
                return inventoryRepository.findByCompanyId(companyId);
        }
}