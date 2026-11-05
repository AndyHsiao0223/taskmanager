package com.andy.taskmanager.service;

import com.andy.taskmanager.dto.CategoryRequest;
import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.entity.Category;
import com.andy.taskmanager.exception.CategoryNotFoundException;
import com.andy.taskmanager.mapper.CategoryMapper;
import com.andy.taskmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  public CategoryResponse getCategoryById(Long id) {
    Category retrievedCategory = categoryRepository.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException(id));

    return categoryMapper.toResponse(retrievedCategory);
  }

  @Transactional
  public CategoryResponse createCategory(CategoryRequest category) {
    Category entityCategory = categoryMapper.toEntity(category);
    Category savedCategory = categoryRepository.save(entityCategory);

    return categoryMapper.toResponse(savedCategory);
  }
}
