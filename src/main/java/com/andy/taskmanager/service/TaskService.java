package com.andy.taskmanager.service;

import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.exception.TaskNotFoundException;
import com.andy.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

  public Task getTaskById(Long id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));
  }

  @Transactional
  public Task createTask(Task task) {
    return taskRepository.save(task);
  }

  @Transactional
  public Task updateTask(Long id, Task updatedTask) {
    // Find, Judge
    Task existingTask = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));

    // Update
    existingTask.setTitle(updatedTask.getTitle());
    existingTask.setDescription(updatedTask.getDescription());
    existingTask.setCompleted(updatedTask.getCompleted());

    // Save, Return
    return taskRepository.save(existingTask);
  }

  @Transactional
  public void deleteTask(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));
    
    taskRepository.delete(task);
  }

  public List<Task> getTasksByCompletionStatus(boolean status) {
    return taskRepository.findByCompleted(status);
  }

  public List<Task> searchTasksByTitle(String title) {
    return taskRepository.findByTitleContainingIgnoreCase(title);
  }
}
