package com.andy.taskmanager.service;

import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.exception.TaskNotFoundException;
import com.andy.taskmanager.mapper.TaskMapper;
import com.andy.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
  }

  public List<TaskResponse> getAllTasks() {
    List<Task> retrievedTasks = taskRepository.findAll();

    return taskMapper.toResponseList(retrievedTasks);
  }

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

  public List<TaskResponse> getTasksByCompletionStatus(boolean status) {
    List<Task> retrievedTasks = taskRepository.findByCompleted(status);

    return taskMapper.toResponseList(retrievedTasks);
  }

  public List<TaskResponse> searchTasksByTitle(String title) {
    List<Task> retrievedTasks = taskRepository.findByTitleContainingIgnoreCase(title);

    return taskMapper.toResponseList(retrievedTasks);
  }
}
