package com.eta.houzezbackend.exception;


import com.eta.houzezbackend.dto.ErrorDto;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), List.of(e.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleArgumentNotValid(MethodArgumentNotValidException e) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

    @ExceptionHandler(value = {UniqueEmailViolationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDto handleUniqueEmailViolationException(UniqueEmailViolationException e) {
        return new ErrorDto(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), List.of(Objects.requireNonNull(e.getMessage())));
    }

    @ExceptionHandler(value = {LinkExpiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleLinkExpiredExceptions(JwtException e) {
        return new ErrorDto(HttpStatus.UNAUTHORIZED.getReasonPhrase(), List.of(e.getMessage()));
    }


    @ExceptionHandler(value = {EmailAddressException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleEmailAddressException(EmailAddressException e) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(e.getMessage()));
    }

    @ExceptionHandler(value = {PropertyNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handlePropertyNotFoundException(PropertyNotFoundException e) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(e.getMessage()));
    }

    @ExceptionHandler(value = {AgentInactiveException.class})
    @ResponseStatus(FORBIDDEN)
    public ErrorDto handleAgentInactiveException(AgentInactiveException e) {
        return new ErrorDto(FORBIDDEN.getReasonPhrase(), List.of(e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleUnexpectedExceptions(Exception e) {
        log.error("There is an unexpected exception occurred", e);

        return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), List.of(e.getMessage()));
    }
}
