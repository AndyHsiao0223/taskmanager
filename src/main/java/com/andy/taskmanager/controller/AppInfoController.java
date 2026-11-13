package com.andy.taskmanager.controller;

import com.andy.taskmanager.config.AppProperties;
import com.andy.taskmanager.dto.AppInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/app-info")
public class AppInfoController {

  private final AppProperties appProperties;

  public AppInfoController(AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  @GetMapping
  public AppInfoResponse getAppInfo() {
    return new AppInfoResponse(
        appProperties.name(),
        appProperties.version(),
        appProperties.maxTasksPerPage()
    );
  }
}
