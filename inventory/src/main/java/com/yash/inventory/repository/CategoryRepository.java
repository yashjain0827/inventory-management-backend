package com.yash.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.inventory.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCompanyId(Long companyId);

}