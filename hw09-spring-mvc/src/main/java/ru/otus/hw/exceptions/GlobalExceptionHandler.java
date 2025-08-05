package ru.otus.hw.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handeEntityNotFoundException(EntityNotFoundException ex) {
        return new ModelAndView("error",
                "errorText", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handeNullPointerException(Exception ex) {
        return new ModelAndView("error",
                "errorText", "Something went wrong...");
    }
}
