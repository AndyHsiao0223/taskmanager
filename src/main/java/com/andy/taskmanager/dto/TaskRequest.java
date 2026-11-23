package com.andy.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequest(
    @NotBlank(message = "Title is mandatory")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    String title,

    @Size(max = 500, message = "Description can be up to 500 characters")
    String description,

    @NotNull(message = "Completed is mandatory")
    Boolean completed,

    @NotNull(message = "Category is mandatory")
    Long categoryId
) {
}
