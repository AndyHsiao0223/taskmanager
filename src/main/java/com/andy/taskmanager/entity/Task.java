package com.andy.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
// @SoftDelete (after remove lombok can use)
@Data // getter, setter, @Override toString(), @Override hashCode(), @Override equals()
@NoArgsConstructor // a null base constructor can fill data in
@AllArgsConstructor // faster to add new object within all data without using lots of setter
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Title is mandatory")
  @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
  @Column(nullable = false, length = 100)
  private String title;

  @Size(max = 500, message = "Description can be up to 500 characters")
  @Column(length = 500)
  private String description;

  @NotNull
  @Column(nullable = false)
  private Boolean completed = false;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.completed = false;
    this.createdAt = LocalDateTime.now();
  }
}
