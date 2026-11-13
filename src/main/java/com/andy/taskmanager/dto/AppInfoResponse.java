package com.andy.taskmanager.dto;

public record AppInfoResponse(
    String name,
    String version,
    int maxTasksPerPage
) {
}
