package com.andy.taskmanager.mapper;

import com.andy.taskmanager.dto.CategoryRequest;
import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category toEntity(CategoryRequest request) {
    return Category.builder()
        .name(request.name())
        .description((request.description()))
        .build();
  }

  public CategoryResponse toResponse(Category category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .description((category.getDescription()))
        .build();


  }
}
