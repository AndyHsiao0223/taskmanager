package com.andy.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // getter, setter, @Override toString(), @Override hashCode(), @Override equals()
@NoArgsConstructor // a null base constructor can fill data in
@AllArgsConstructor // faster to add new object within all data without using lots of setter
public class Task {
    private String id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
}
