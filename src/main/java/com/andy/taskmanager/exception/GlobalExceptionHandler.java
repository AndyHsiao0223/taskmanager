package com.andy.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Validation Failed");
    problemDetail.setTitle("Bad Request");

    List<Map<String, String>> errors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> Map.of(
            "field", error.getField(),
            "message", Objects.requireNonNullElse(error.getDefaultMessage(), "Validation error")
        ))
        .toList();
    problemDetail.setProperty("errors", errors);

    return handleExceptionInternal(exception, problemDetail, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception exception, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

    ResponseEntity<Object> response = super.handleExceptionInternal(exception, body, headers, statusCode, request);

    if (response != null && response.getBody() instanceof ProblemDetail problemDetail) {
      problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
      problemDetail.setProperty("timestamp", LocalDateTime.now());
    }

    return response;
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                             HttpServletRequest request) {

    log.warn("Data integrity violation at path: {}", request.getRequestURI(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "The request conflicts with " +
        "the current state of the data");

    problemDetail.setTitle("Conflict");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", LocalDateTime.now());

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
