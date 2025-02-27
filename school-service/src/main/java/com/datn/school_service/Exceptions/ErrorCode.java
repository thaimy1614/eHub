package com.datn.school_service.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.text.MessageFormat;

@Getter

public enum ErrorCode {
    UNAUTHENTICATED(1111, "Unauthenticated", HttpStatus.OK),
    ENTITYS_NOT_FOUND(2001, "The requested find {0} by ID {1} was not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST(2002, "Invalid request for {0}", HttpStatus.BAD_REQUEST),
    FAILED_SAVE_ENTITY(2003, "Failed to save {0} entity", HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_EMPTY(2004, "The request get all {0} was not found", HttpStatus.NOT_FOUND),
    INPUT_NULL(2005, "{0) is null ", HttpStatus.BAD_REQUEST),
    ENTITY_ALREADY_EXIT(2006, "{0} aldeary study at this {1}", HttpStatus.CONFLICT),
    ;
    private final int code;
    private final String messageTemplate;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String messageTemplate, HttpStatusCode statusCode) {
        this.code = code;
        this.messageTemplate = messageTemplate;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(messageTemplate, args);
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
