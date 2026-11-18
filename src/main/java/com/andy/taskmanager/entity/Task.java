package com.andy.taskmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
// 给框架用：Hibernate 查库需要先建空壳，再塞数据
@NoArgsConstructor
// 给自己用：快速 new 出一个包含所有字段的完整对象
@AllArgsConstructor
@Builder
@SoftDelete
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
