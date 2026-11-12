package com.andy.taskmanager.mapper;

import com.andy.taskmanager.dto.CategoryResponse;
import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

// TODO: Refactor to MapStruct
@Component
public class TaskMapper {

  public Task toEntity(TaskRequest request) {
    return Task.builder()
        .title(request.title())
        .description(request.description())
        .completed(request.completed())
        .build();
  }

  public TaskResponse toResponse(Task task) {
    CategoryResponse categoryResponse = null;
    if (task.getCategory() != null) {
      categoryResponse = new CategoryResponse(
          task.getCategory().getId(),
          task.getCategory().getName(),
          task.getCategory().getDescription()
      );
    }

    return TaskResponse.builder()
        .id(task.getId())
        .title(task.getTitle())
        .description(task.getDescription())
        .completed(task.getCompleted())
        .createdAt(task.getCreatedAt())
        .category(categoryResponse)
        .build();
  }

  public void updateEntityFromRequest(Task task, TaskRequest request) {
    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setCompleted(request.completed());
  }

/*
  public List<TaskResponse> toResponseList(List<Task> tasks) {
    return tasks.stream()
        .map(this::toResponse)
        .toList();
  }
*/
}
