package com.workshop.ecommerce.service.impl;

import com.workshop.ecommerce.dto.CategoryDTO;
import com.workshop.ecommerce.exception.ResourceAlreadyExistsException;
import com.workshop.ecommerce.exception.ResourceNotFoundException;
import com.workshop.ecommerce.model.Category;
import com.workshop.ecommerce.repository.CategoryRepository;
import com.workshop.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Check if category with the same name already exists
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new ResourceAlreadyExistsException("Category with name '" + categoryDTO.getName() + "' already exists");
        }
        
        // Map DTO to entity
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        // Save category
        Category savedCategory = categoryRepository.save(category);
        
        // Return mapped DTO
        return mapToDTO(savedCategory);
    }
    
    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        return mapToDTO(category);
    }
    
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        
        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if category with the new name already exists (if name is being changed)
        if (!category.getName().equals(categoryDTO.getName()) && 
                categoryRepository.existsByName(categoryDTO.getName())) {
            throw new ResourceAlreadyExistsException("Category with name '" + categoryDTO.getName() + "' already exists");
        }
        
        // Update fields
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        // Save updated category
        Category updatedCategory = categoryRepository.save(category);
        
        return mapToDTO(updatedCategory);
    }
    
    @Override
    @Transactional
    public void deleteCategory(Long id) {
        // Check if category exists
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        
        categoryRepository.deleteById(id);
    }
    
    // Helper method: Map Entity to DTO
    private CategoryDTO mapToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setProductCount(category.getProducts().size());
        
        return categoryDTO;
    }
}
