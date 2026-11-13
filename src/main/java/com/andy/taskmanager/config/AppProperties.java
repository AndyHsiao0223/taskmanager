package com.andy.taskmanager.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProperties(
    @NotBlank(message = "App name cannot be empty")
    String name,

    @NotBlank(message = "App version cannot be empty")
    String version,

    @Min(1)
    @Max(50)
    int maxTasksPerPage
) {
}
