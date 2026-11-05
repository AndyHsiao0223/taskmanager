package com.andy.taskmanager.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
    Long id,
    String name,
    String description
) {
}
