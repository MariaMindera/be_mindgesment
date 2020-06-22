package com.mindera.school.mindgesment.http.advices;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mindera.school.mindgesment.http.models.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.DateTimeException;

/**
 * ExceptionHandlerController class exists to handle java errors.
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleUnknown(Exception e) {
        logger.error(e.getMessage());
        return new ApiError("INTERNAL_SERVER_ERROR", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_REQUEST", e.getMessage(), e.getParameter().getParameterName());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ApiError handleInvalidFormatException(InvalidFormatException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiError handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiError handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage());
        return new ApiError("METHOD_NOT_ALLOWED", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ApiError handleNullPointerException(NullPointerException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_REQUEST", null, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeException.class)
    public ApiError dateTimeException(DateTimeException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_DATE_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiError missingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error(e.getMessage());
        return new ApiError("BAD_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(InvalidEndpointRequestException.class)
    public ApiError invalidEndpointRequestException(InvalidEndpointRequestException e) {
        logger.error(e.getMessage());
        return new ApiError("METHOD_NOT_ALLOWED", e.getMessage(), e.getReason());
    }
}