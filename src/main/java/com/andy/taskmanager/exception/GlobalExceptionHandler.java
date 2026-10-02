package com.andy.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleTaskNotFoundException(TaskNotFoundException exception, HttpServletRequest request) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", exception.getMessage());
    body.put("path", request.getRequestURI());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGlobalException(HttpServletRequest request) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "An unexpected error occurred");
    body.put("path", request.getRequestURI());

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
