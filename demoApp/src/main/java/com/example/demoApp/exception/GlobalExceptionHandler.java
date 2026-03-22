package com.example.demoApp.exception;

// import java.net.http.HttpRequest;
import java.time.LocalDateTime;

// import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public LocalDateTime currentTime = LocalDateTime.now();
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest req){

        ApiError error = new ApiError (currentTime, HttpStatus.CONFLICT.value(), ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest req){
            ApiError err = new ApiError(currentTime, HttpStatus.NOT_FOUND.value(), ex.getMessage(), req.getRequestURI());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidationError(
                MethodArgumentNotValidException ex,
                HttpServletRequest request) {

            String message = ex.getBindingResult()
                    .getFieldError()
                    .getDefaultMessage();

            ApiError error = new ApiError(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    message,
                    request.getRequestURI()
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
