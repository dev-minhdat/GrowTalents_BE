package com.growtalents.exception;

import com.growtalents.dto.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi validate DTO (NotBlank, Email,...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(GlobalResponse.error("Validation Failed", 400));
    }

    // Xử lý lỗi parse date format không đúng
    @ExceptionHandler({DateTimeParseException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<GlobalResponse<Void>> handleDateParseException(Exception ex) {
        String message = "Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy";
        return ResponseEntity.badRequest().body(GlobalResponse.error(message, 400));
    }

    // Xử lý lỗi JSON parse
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse<Void>> handleJsonParseException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(GlobalResponse.error("Dữ liệu JSON không hợp lệ", 400));
    }

    // Xử lý các lỗi Runtime như không tìm thấy bản ghi
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse.error(ex.getMessage(), 400));
    }

    // Bắt fallback cho các lỗi khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Void>> handleAllOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GlobalResponse.error("Lỗi hệ thống: " + ex.getMessage(), 500));
    }
}
