package com.example.todo.todo.exception;

import lombok.Getter;

public enum Exception {
    TODO_NOT_FOUND(404, "Todo not found");

    @Getter
    private int code;

    @Getter
    private String message;

    Exception(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
