package com.tecnova.task.api.exception;

public class NotFoundTaskException extends RuntimeException{

    public NotFoundTaskException(String message) {
        super(message);
    }
}
