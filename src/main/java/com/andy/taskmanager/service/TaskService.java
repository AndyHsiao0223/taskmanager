package com.andy.taskmanager.service;

import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.exception.TaskNotFoundException;
import com.andy.taskmanager.mapper.TaskMapper;
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

  public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
  }

  public Page<TaskResponse> searchTasks(String title, Boolean completed, Pageable pageable) {
    Specification<Task> spec = Specification.where(TaskSpecification.hasTitle(title))
            .and(TaskSpecification.hasCompleted(completed));

    return taskRepository.findAll(spec, pageable)
            .map(taskMapper::toResponse);
  }

/*
  public Page<TaskResponse> getAllTasks(Pageable pageable) {
    Page<Task> retrievedTasks = taskRepository.findAll(pageable);

    return retrievedTasks.map(taskMapper::toResponse);
  }

  public Page<TaskResponse> getTasksByCompletionStatus(boolean status, Pageable pageable) {
    Page<Task> retrievedTasks = taskRepository.findByCompleted(status, pageable);

    return retrievedTasks.map(taskMapper::toResponse);
  }

  public Page<TaskResponse> searchTasksByTitle(String title, Pageable pageable) {
    Page<Task> retrievedTasks = taskRepository.findByTitleContainingIgnoreCase(title, pageable);

    return retrievedTasks.map(taskMapper::toResponse);
  }
*/

  public TaskResponse getTaskById(Long id) {
    Task retrievedTask = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    return taskMapper.toResponse(retrievedTask);
  }

  @Transactional
  public TaskResponse createTask(TaskRequest task) {
    Task entityTask = taskMapper.toEntity(task);
    Task savedTask = taskRepository.save(entityTask);
    return taskMapper.toResponse(savedTask);
  }

  @Transactional
  public TaskResponse updateTask(Long id, TaskRequest updatedTask) {
    // Find, Judge
    Task existingTask = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    // Update
    taskMapper.updateEntityFromRequest(existingTask, updatedTask);

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
