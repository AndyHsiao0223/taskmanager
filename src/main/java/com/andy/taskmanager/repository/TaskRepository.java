package com.andy.taskmanager.repository;

import com.andy.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
  // Paginated methods
  Page<Task> findByCompleted(boolean completed, Pageable pageable);
  Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  /*
    Method Name Query
    List<Task> findByCompleted(boolean completed);
    List<Task> findByTitleContainingIgnoreCase(String title);
  */
  /*
    JPQL (java persistence query language)
    @Query("SELECT t FROM Task t WHERE t.completed = :completed")
    Page<Task> findTasksByCompletionStatus(@Param("completed") boolean completed, Pageable pageable);
  */
}
