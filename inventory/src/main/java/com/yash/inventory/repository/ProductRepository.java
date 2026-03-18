package com.yash.inventory.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCompanyId(Long companyId, Pageable pageable);

    Optional<Product> findByIdAndCompanyId(Long id, Long companyId);
}