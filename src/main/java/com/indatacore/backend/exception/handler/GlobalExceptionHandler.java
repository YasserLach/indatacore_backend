package com.indatacore.backend.exception.handler;

import com.indatacore.backend.dto.ErrorResponse;
import com.indatacore.backend.dto.ValidationResponse;
import com.indatacore.backend.exception.BusinessException;
import com.indatacore.backend.exception.ElementAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        String message = errorMessage != null ? errorMessage.split(":")[0] : "Unknown error";

        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(BAD_REQUEST.value())
                        .status(BAD_REQUEST)
                        .message(message)
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ValidationResponse> validations = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ValidationResponse.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(validations);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build());

    }

    @ExceptionHandler(value = ElementAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleException(final ElementAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder().code(HttpStatus.CONFLICT.value()).status(HttpStatus.CONFLICT).message(e.getMessage()).build());

    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(final IllegalArgumentException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.builder().code(BAD_REQUEST.value()).status(BAD_REQUEST).message(e.getMessage()).build());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        String errorMessage = ex.getMessage();
        String message = errorMessage != null ? errorMessage.split(":")[0] : "Unknown error";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(message)
                        .build());
    }


}
