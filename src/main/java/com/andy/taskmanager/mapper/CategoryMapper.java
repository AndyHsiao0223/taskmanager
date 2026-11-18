package com.andy.taskmanager.mapper;

import com.andy.taskmanager.dto.CategoryRequest;
import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category toEntity(CategoryRequest request);

  CategoryResponse toResponse(Category category);
}
