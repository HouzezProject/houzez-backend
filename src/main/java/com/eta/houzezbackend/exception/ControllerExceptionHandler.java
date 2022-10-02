package com.eta.houzezbackend.exception;

import com.eta.houzezbackend.dto.DuplicateEmailCheckDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DuplicateEmailCheckDto handleResourceNotFoundException(ResourceNotFoundException e) {
        return new DuplicateEmailCheckDto(false, List.of(e.getMessage()).toString());
    }
}
