package com.andy.taskmanager.repository;

import com.andy.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// Extends JpaSpecificationExecutor to support dynamic queries (filtering, pagination).
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
  /*
   * Architecture Note:
   * Previously used derived queries(Method name query) & JPQL(java persistence query language) for task filtering,
   * but migrated to JpaSpecificationExecutor + TaskSpecification
   * to support highly dynamic multi-conditional searches without code bloat.
   *
   * (See TaskSpecification.java for actual filter logic)
   */
}
