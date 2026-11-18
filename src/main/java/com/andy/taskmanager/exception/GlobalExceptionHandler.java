package com.andy.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(TaskNotFoundException.class)
  public ProblemDetail handleTaskNotFoundException(TaskNotFoundException exception, HttpServletRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

    problemDetail.setTitle("Task Not Found");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", LocalDateTime.now());

    return problemDetail;
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ProblemDetail handleCategoryNotFoundException(CategoryNotFoundException exception, HttpServletRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

    problemDetail.setTitle("Category Not Found");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", LocalDateTime.now());

    return problemDetail;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");

    problemDetail.setTitle("Bad Request");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", LocalDateTime.now());

    List<Map<String, String>> errors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> Map.of(
            "field", error.getField(),
            "message", Objects.requireNonNullElse(error.getDefaultMessage(), "Validation error")
        ))
        .toList();
    problemDetail.setProperty("errors", errors);

    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGlobalException(Exception exception, HttpServletRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");

    log.error("Unexpected error at path: {}", request.getRequestURI(), exception);

    problemDetail.setTitle("Internal Server Error");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", LocalDateTime.now());

    return problemDetail;
  }
}
