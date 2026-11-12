package com.andy.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
// @SoftDelete (after remove lombok can use)
@Data // getter, setter, @Override toString(), @Override hashCode(), @Override equals()
@NoArgsConstructor // a null base constructor can fill data in
@AllArgsConstructor // faster to add new object within all data without using lots of setter
@Builder
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  private Boolean completed = false;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @PrePersist
  protected void onCreate() {
    this.completed = false;
    this.createdAt = LocalDateTime.now();
  }
}
