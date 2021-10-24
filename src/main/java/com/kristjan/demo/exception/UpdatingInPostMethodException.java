package com.kristjan.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception should be thrown when ID is set on object that has been received via POST method.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UpdatingInPostMethodException extends RuntimeException  {
    public UpdatingInPostMethodException() {
        super();
    }
}
