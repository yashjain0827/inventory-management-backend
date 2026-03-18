package com.yash.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    List<Inventory> findByCompanyId(Long companyId);

    Optional<Inventory> findByProductIdAndWarehouseIdAndCompanyId(
            Long productId, Long warehouseId, Long companyId);
}
