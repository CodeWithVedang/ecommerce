package com.workshop.ecommerce.service;

import com.workshop.ecommerce.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    
    CategoryDTO getCategoryById(Long id);
    
    List<CategoryDTO> getAllCategories();
    
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    
    void deleteCategory(Long id);
}

