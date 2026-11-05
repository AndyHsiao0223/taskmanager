package com.andy.taskmanager.controller;

import com.andy.taskmanager.dto.CategoryRequest;
import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/{id}")
  public CategoryResponse getCategoryById(@PathVariable Long id) {
    return categoryService.getCategoryById(id);
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest category) {
    CategoryResponse savedCategory = categoryService.createCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
  }
}
