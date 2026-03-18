package com.yash.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.inventory.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findByCompanyId(Long companyId);
}