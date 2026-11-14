package com.andy.taskmanager.service;

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

    if (request.categoryId() != null) {
      Category category = categoryRepository.findById(request.categoryId())
          .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

      entityTask.setCategory(category);
    }

    Task savedTask = taskRepository.save(entityTask);
    return taskMapper.toResponse(savedTask);
  }

  @Transactional
  public TaskResponse updateTask(Long id, TaskRequest request) {
    // Find, Judge
    Task existingTask = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    // Update
    taskMapper.updateEntityFromRequest(existingTask, request);

    if (request.categoryId() != null) {
      Category category = categoryRepository.findById(request.categoryId())
          .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

      existingTask.setCategory(category);
    }

    // Save, Return
    Task savedTask = taskRepository.save(existingTask);
    return taskMapper.toResponse(savedTask);
  }

  @Transactional
  public void deleteTask(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    taskRepository.delete(task);
  }
}
