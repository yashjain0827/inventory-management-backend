package com.yash.inventory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yash.inventory.dto.ProductRequest;
import com.yash.inventory.entity.Category;
import com.yash.inventory.entity.Company;
import com.yash.inventory.entity.Product;
import com.yash.inventory.entity.Supplier;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CategoryRepository;
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.repository.ProductRepository;
import com.yash.inventory.repository.SupplierRepository;
import com.yash.inventory.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CompanyRepository companyRepository;

    public String createProduct(ProductRequest request) {

        Long companyId = SecurityUtils.getCurrentCompanyId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(category)
                .supplier(supplier)
                .company(company) // 🔥 IMPORTANT
                .build();

        productRepository.save(product);

        return "Product created successfully";
    }

    public Page<Product> getAllProducts(int page, int size) {

        Long companyId = SecurityUtils.getCurrentCompanyId();

        return productRepository.findByCompanyId(companyId, PageRequest.of(page, size));
    }

    public Product getProductById(Long id) {

        Long companyId = SecurityUtils.getCurrentCompanyId();

        return productRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public String updateProduct(Long id, ProductRequest request) {

        Product product = getProductById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        productRepository.save(product);

        return "Product updated successfully";
    }

    public String deleteProduct(Long id) {

        Product product = getProductById(id); // already filtered

        productRepository.delete(product);

        return "Product deleted successfully";
    }
}