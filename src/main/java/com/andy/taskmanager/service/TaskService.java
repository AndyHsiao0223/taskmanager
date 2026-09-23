package com.andy.taskmanager.service;

import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public Optional<Task> getTaskById(Long id) {
    return taskRepository.findById(id);
  }

  @Transactional
  public Task createTask(Task task) {
    return taskRepository.save(task);
  }

  @Transactional
  public Optional<Task> updateTask(Long id, Task updatedTask) {
    // Find
    Task existingTask = taskRepository.findById(id).orElse(null);

    // Judge
    if (existingTask == null) {
      return Optional.empty();
    }

    // Update
    existingTask.setTitle(updatedTask.getTitle());
    existingTask.setDescription(updatedTask.getDescription());
    existingTask.setCompleted(updatedTask.getCompleted());

    // Save, Return
    Task savedTask = taskRepository.save(existingTask);
    return Optional.of(savedTask);
  }

  @Transactional
  public boolean deleteTask(Long id) {
    if (!taskRepository.existsById(id)) {
      return false;
    }

    taskRepository.deleteById(id);
    // 204 No Content
    return true;
  }

  public List<Task> getTasksByCompletionStatus(boolean status) {
    return taskRepository.findByCompleted(status);
  }

  public List<Task> searchTasksByTitle(String title) {
    return taskRepository.findByTitleContainingIgnoreCase(title);
  }
}
