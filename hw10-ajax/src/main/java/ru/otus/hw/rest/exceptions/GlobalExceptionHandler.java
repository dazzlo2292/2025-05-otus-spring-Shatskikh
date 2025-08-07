package ru.otus.hw.rest.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_STATUS = "error";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handeNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorDto(ERROR_STATUS, ex.getMessage()));
    }
}
