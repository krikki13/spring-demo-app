package com.kristjan.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UpdatingInPostMethodException extends RuntimeException  {
    public UpdatingInPostMethodException() {
        super();
    }
}
