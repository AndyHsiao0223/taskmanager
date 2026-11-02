package com.andy.taskmanager.repository;

import com.andy.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
/*
  Paginated methods
  Page<Task> findByCompleted(boolean completed, Pageable pageable);
  Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);
*/

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
