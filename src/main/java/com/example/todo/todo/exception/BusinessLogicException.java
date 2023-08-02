package com.example.todo.todo.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {

    @Getter
    private Exception exception;

    public BusinessLogicException(Exception exception) {
        super(exception.getMessage());
        this.exception = exception;
    }
}
