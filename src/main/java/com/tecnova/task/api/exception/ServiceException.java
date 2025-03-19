package com.tecnova.task.api.exception;

public class ServiceException extends RuntimeException{
    private final String code;
    public ServiceException(ErrorCodes errorCodes) {
        super(errorCodes.getMessage());
        this.code=errorCodes.getCode();
    }
}
