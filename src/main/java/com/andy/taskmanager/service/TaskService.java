package com.andy.taskmanager.service;

import com.andy.taskmanager.dto.TaskPatchRequest;
import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.entity.Category;
import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.exception.CategoryNotFoundException;
import com.andy.taskmanager.exception.TaskNotFoundException;
import com.andy.taskmanager.mapper.TaskMapper;
import com.andy.taskmanager.repository.CategoryRepository;
import com.andy.taskmanager.repository.TaskRepository;
import com.andy.taskmanager.repository.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;
  private final CategoryRepository categoryRepository;

  public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, CategoryRepository categoryRepository) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
    this.categoryRepository = categoryRepository;
  }

  public Page<TaskResponse> searchTasks(String title, Boolean completed, Pageable pageable) {
    // 1. Assemble dynamic conditions (null conditions are automatically ignored).
    Specification<Task> spec = Specification.where(TaskSpecification.hasTitle(title))
            .and(TaskSpecification.hasCompleted(completed));

    // 2. Execute query with Specification and Pagination.
    return taskRepository.findAll(spec, pageable)
            .map(taskMapper::toResponse);
  }

  public TaskResponse getTaskById(Long id) {
    Task retrievedTask = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    return taskMapper.toResponse(retrievedTask);
  }

  @Transactional
  public TaskResponse createTask(TaskRequest request) {
    Task entityTask = taskMapper.toEntity(request);

    String description = request.description();
    if (description != null) {
      entityTask.setDescription(description.isBlank() ? null : description);
    }

    entityTask.setCategory(findCategory(request.categoryId()));

    Task savedTask = taskRepository.save(entityTask);
    return taskMapper.toResponse(savedTask);
  }

  @Transactional
  public TaskResponse patchTask(Long id, TaskPatchRequest request) {
    Task existingTask = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    taskMapper.patchEntity(request, existingTask);

    String description = request.description();
    if (description != null) {
      existingTask.setDescription(description.isBlank() ? null : description);
    }

    if (request.categoryId() != null) {
      existingTask.setCategory(findCategory(request.categoryId()));
    }

    Task savedTask = taskRepository.save(existingTask);
    return taskMapper.toResponse(savedTask);
  }

  @Transactional
  public void deleteTask(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    taskRepository.delete(task);
  }

  private Category findCategory(Long categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
  }
}
