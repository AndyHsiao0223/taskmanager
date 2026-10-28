package com.andy.taskmanager.controller;

import com.andy.taskmanager.dto.TaskRequest;
import com.andy.taskmanager.dto.TaskResponse;
import com.andy.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<Page<TaskResponse>> getAllTasks(
          @PageableDefault(
                  page = 0,
                  size = 10,
                  // frontend query params use: "sort=createdAt,desc"
                  sort = "createdAt",
                  direction = Sort.Direction.DESC
          ) Pageable pageable
  ) {
    return ResponseEntity.ok(taskService.getAllTasks(pageable));
  }

  @GetMapping("/{id}")
  public TaskResponse getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id);
  }

  @PostMapping
  public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest task) {
    TaskResponse savedTask = taskService.createTask(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
  }

  @PutMapping("/{id}")
  public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest updatedTask) {
    return taskService.updateTask(id, updatedTask);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/completed/{status}")
  public ResponseEntity<Page<TaskResponse>> getTasksByCompletionStatus(
          @PathVariable boolean status,
          @PageableDefault(
                  page = 0,
                  size = 10,
                  sort = "createdAt",
                  direction = Sort.Direction.DESC
          ) Pageable pageable
  ) {
    Page<TaskResponse> tasks = taskService.getTasksByCompletionStatus(status, pageable);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<TaskResponse>> searchTasksByTitle(
          @RequestParam String title,
          @PageableDefault(
                  page = 0,
                  size = 10,
                  sort = "createdAt",
                  direction = Sort.Direction.DESC
          ) Pageable pageable
  ) {
    Page<TaskResponse> tasks = taskService.searchTasksByTitle(title, pageable);
    return ResponseEntity.ok(tasks);
  }
}
