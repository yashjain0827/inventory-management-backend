package com.yash.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.inventory.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCompanyId(Long companyId);
}