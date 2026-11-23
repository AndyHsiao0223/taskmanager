package com.andy.taskmanager.controller;

import com.andy.taskmanager.dto.TaskPatchRequest;
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

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public ResponseEntity<Page<TaskResponse>> searchTasks(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) Boolean completed,
          @PageableDefault(
                  page = 0,
                  size = 10,
              // e.g. ?sort=createdAt,desc
                  sort = "createdAt",
                  direction = Sort.Direction.DESC
          ) Pageable pageable
  ) {
    Page<TaskResponse> tasks = taskService.searchTasks(title, completed, pageable);
    return ResponseEntity.ok(tasks);
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

  @PatchMapping("/{id}")
  public TaskResponse patchTask(@PathVariable Long id, @Valid @RequestBody TaskPatchRequest request) {
    return taskService.patchTask(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
}
