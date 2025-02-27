package com.datn.user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.OK),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.OK),
    USER_EXISTED(1002, "User existed", HttpStatus.OK),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.OK),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.OK),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.OK),
    USERNAME_OR_PASSWORD_INCORRECT(1008, "Username or Password is incorrect", HttpStatus.OK),
    INCORRECT_PASSWORD(1009, "Password is incorrect", HttpStatus.OK),
    UNAUTHENTICATED(1111, "Unauthenticated", HttpStatus.OK),
    UNAUTHORIZED(2222, "You do not have permission", HttpStatus.OK),
    FILE_ERROR(1006, "File error", HttpStatus.OK),
    INVALID_DATE_FORMAT(1007, "Invalid date format", HttpStatus.OK),
    VerificationLinkCorrupted(1008, "Verification link corrupted", HttpStatus.OK),
    AccountAlreadyVerified(1009, "Account already verified", HttpStatus.OK),
    AccountIsBlocked(1010, "Account is blocked", HttpStatus.OK),
    VerificationLinkExpired(1011, "Verification link expired", HttpStatus.OK),
    UnverifiedAccount(1012, "Unverified account", HttpStatus.OK),
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