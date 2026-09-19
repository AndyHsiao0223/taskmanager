package com.andy.taskmanager.repository;

import com.andy.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
  // Method Name Query
  List<Task> findByCompleted(boolean completed);
  /*
   JPQL (java persistence query language)
   @Query("SELECT t FROM Task t WHERE t.completed = :completed")
   List<Task> findTasksByCompletionStatus(@Param("completed") boolean completed);
  */

  List<Task> findByTitleContainingIgnoreCase(String title);
}
