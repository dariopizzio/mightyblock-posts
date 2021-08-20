package com.mightyblock.posts.config.error;

import com.mightyblock.posts.dto.ApiErrorDto;
import com.mightyblock.posts.model.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler
 * Every handler should return an ApiErrorDto to normalize the response
 * in case of error
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {

    /**
     * Handle all the ApiExceptions threw by the application
     * @param exception exception handled (ApiException)
     * @return ApiErrorDto
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorDto> handleException(Exception exception) {
        ApiException apiException = (ApiException) exception;
        return new ResponseEntity<>(apiException.getApiError(), apiException.getStatus());
    }
}
