package com.workshop.ecommerce.service.impl;

import com.workshop.ecommerce.dto.ProductDTO;
import com.workshop.ecommerce.exception.ResourceNotFoundException;
import com.workshop.ecommerce.model.Category;
import com.workshop.ecommerce.model.Product;
import com.workshop.ecommerce.repository.CategoryRepository;
import com.workshop.ecommerce.repository.ProductRepository;
import com.workshop.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Find category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        
        // Map DTO to entity
        Product product = mapToEntity(productDTO);
        product.setCategory(category);
        
        // Save product
        Product savedProduct = productRepository.save(product);
        
        // Return mapped DTO
        return mapToDTO(savedProduct);
    }
    
    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        return mapToDTO(product);
    }
    
    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::mapToDTO);
    }
    
    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        // Update fields
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        
        // Update category if provided
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
            product.setCategory(category);
        }
        
        // Save updated product
        Product updatedProduct = productRepository.save(product);
        
        return mapToDTO(updatedProduct);
    }
    
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        // Check if product exists
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        
        productRepository.deleteById(id);
    }
    
    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        // Check if category exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        
        List<Product> products = productRepository.findByCategoryId(categoryId);
        
        return products.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<ProductDTO> searchProducts(String keyword, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return products.map(this::mapToDTO);
    }
    
    @Override
    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        
        return products.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDTO> getLowStockProducts() {
        List<Product> products = productRepository.findLowStockProducts();
        
        return products.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // Helper method: Map Entity to DTO
    private ProductDTO mapToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setImageUrl(product.getImageUrl());
        
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setCategoryName(product.getCategory().getName());
        }
        
        return productDTO;
    }
    
    // Helper method: Map DTO to Entity
    private Product mapToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        
        return product;
    }
}
