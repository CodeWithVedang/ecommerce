package com.workshop.ecommerce.service;

import com.workshop.ecommerce.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    
    ProductDTO createProduct(ProductDTO productDTO);
    
    ProductDTO getProductById(Long id);
    
    Page<ProductDTO> getAllProducts(Pageable pageable);
    
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    
    void deleteProduct(Long id);
    
    List<ProductDTO> getProductsByCategory(Long categoryId);
    
    Page<ProductDTO> searchProducts(String keyword, Pageable pageable);
    
    List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<ProductDTO> getLowStockProducts();
}
