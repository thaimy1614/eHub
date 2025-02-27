package com.datn.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1111, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2222, "You do not have permission", HttpStatus.UNAUTHORIZED),
    RESOURCE_NOT_FOUND(6001, "Resource not found", HttpStatus.NOT_FOUND),
    INVALID_KEY(6002, "Invalid key", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}