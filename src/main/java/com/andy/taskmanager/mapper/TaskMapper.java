package com.andy.taskmanager.mapper;

import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.dto.TaskPatchRequest;
import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.entity.Category;
import com.andy.taskmanager.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  Task toEntity(TaskRequest request);

  TaskResponse toResponse(Task task);

  CategoryResponse toCategoryResponse(Category category);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "category", ignore = true)
  void patchEntity(TaskPatchRequest request, @MappingTarget Task task);
}
