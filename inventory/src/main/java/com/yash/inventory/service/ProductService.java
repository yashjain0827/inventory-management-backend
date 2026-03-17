package com.yash.inventory.service;

import com.yash.inventory.dto.ProductRequest;
import com.yash.inventory.entity.Category;
import com.yash.inventory.entity.Product;
import com.yash.inventory.entity.Supplier;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CategoryRepository;
import com.yash.inventory.repository.ProductRepository;
import com.yash.inventory.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public String createProduct(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(category)
                .supplier(supplier)
                .build();
        productRepository.save(product);

        return "Product created successfully";
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public String updateProduct(Long id, ProductRequest request) {

        Product product = getProductById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        productRepository.save(product);

        return "Product updated successfully";
    }

    public String deleteProduct(Long id) {

        Product product = getProductById(id);

        productRepository.delete(product);

        return "Product deleted successfully";
    }
}