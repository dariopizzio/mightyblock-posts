package com.mightyblock.posts.model.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Exception that is threw when a property was not found
 */
@AllArgsConstructor
public class PropertyNotFoundException extends Exception {

    private HttpStatus status;
    private String message;
}
