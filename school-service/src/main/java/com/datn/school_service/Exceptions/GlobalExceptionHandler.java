package com.datn.school_service.Exceptions;

import com.datn.school_service.Dto.Respone.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(errorCode.getCode()) // Mã lỗi bắt đầu từ số 2
                .message(errorCode.getMessage(ex.getArgs()))
                .build();

        return ResponseEntity
                .status(errorCode.getStatusCode().value())
                .body(response);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
//        ApiResponse<Void> response = ApiResponse.<Void>builder()
//                .code(2000) // Mã lỗi mặc định bắt đầu từ số 2
//                .message("An unexpected error occurred")
//                .build();
//
//        return ResponseEntity
//                .status(500)
//                .body(response);
//    }
}
