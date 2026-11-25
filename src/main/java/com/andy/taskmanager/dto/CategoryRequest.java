package com.andy.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    String name,

    @Size(max = 300, message = "Description can be up to 300 characters")
    String description
) {
}
