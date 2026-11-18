package com.andy.taskmanager.mapper;

import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.entity.Category;
import com.andy.taskmanager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  Task toEntity(TaskRequest request);

  TaskResponse toResponse(Task task);

  void updateEntityFromRequest(TaskRequest request, @MappingTarget Task task);

  CategoryResponse toCategoryResponse(Category category);
}
