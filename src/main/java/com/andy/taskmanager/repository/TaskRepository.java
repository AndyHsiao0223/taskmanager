package com.andy.taskmanager.repository;

import com.andy.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
