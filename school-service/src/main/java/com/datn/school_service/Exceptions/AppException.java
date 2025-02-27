package com.datn.school_service.Exceptions;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public AppException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage(args));
        this.errorCode = errorCode;
        this.args = args;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
