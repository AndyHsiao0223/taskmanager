package com.andy.taskmanager.controller;

import com.andy.taskmanager.entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController // @Controller, @ResponseBody
@RequestMapping("/api/v1/tasks") // the basic path use like this: localhost:8080/api/vi/tasks/search-by?status=OPEN
// "/api": Tell our infrastructure "this is backend endpoint, not a webpage"
// "/v1": Provide us a safe way to upgrade without breaking existing users (v1, v2 can run at the same time)
public class TaskController {

    // Temporary in-memory task list
    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById (@PathVariable Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setId(nextId++);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        tasks.add(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        for ( int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId().equals(id)) {
                updatedTask.setId(id);
                updatedTask.setCreatedAt(task.getCreatedAt());
                tasks.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
