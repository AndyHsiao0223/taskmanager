package com.andy.taskmanager.controller;

import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller, @ResponseBody
@RequestMapping("/api/v1/tasks") // the basic path use like this: localhost:8080/api/vi/tasks/search-by?status=OPEN
// "/api": Tell our infrastructure "this is backend endpoint, not a webpage"
// "/v1": Provide us a safe way to upgrade without breaking existing users (v1, v2 can run at the same time)
public class TaskController {

  private final TaskRepository taskRepository;

  //  Constructor Injection from Repository
  public TaskController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @GetMapping
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return taskRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    Task savedTask = taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    // Find
    Task existingTask = taskRepository.findById(id).orElse(null);

    // Judge
    if (existingTask == null) {
      return ResponseEntity.notFound().build();
    }

    // Update
    existingTask.setTitle(updatedTask.getTitle());
    existingTask.setDescription(updatedTask.getDescription());
    existingTask.setCompleted(updatedTask.getCompleted());

    // Save, Return
    Task savedTask = taskRepository.save(existingTask);
    return ResponseEntity.ok(savedTask);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    if (!taskRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    taskRepository.deleteById(id);
    // 204 No Content
    return ResponseEntity.noContent().build();
  }
}
