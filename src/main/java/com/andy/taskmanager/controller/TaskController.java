package com.andy.taskmanager.controller;

import com.andy.taskmanager.entity.Task;
import com.andy.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller, @ResponseBody
@RequestMapping("/api/v1/tasks") // the basic path use like this: localhost:8080/api/vi/tasks/search-by?status=OPEN
// "/api": Tell our infrastructure "this is backend endpoint, not a webpage"
// "/v1": Provide us a safe way to upgrade without breaking existing users (v1, v2 can run at the same time)
public class TaskController {

  private final TaskService taskService;

  //  Constructor Injection from Service
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    Task savedTask = taskService.createTask(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    return taskService.updateTask(id, updatedTask)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    return taskService.deleteTask(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @GetMapping("/completed/{status}")
  public ResponseEntity<List<Task>> getTasksByCompletionStatus(@PathVariable boolean status) {
    List<Task> tasks = taskService.getTasksByCompletionStatus(status);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Task>> searchTasksByTitle(@RequestParam String title) {
    List<Task> tasks = taskService.searchTasksByTitle(title);
    return ResponseEntity.ok(tasks);
  }
}
