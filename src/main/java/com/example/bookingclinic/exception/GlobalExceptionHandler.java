package com.example.bookingclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Bắt các lỗi nghiệp vụ do mình chủ động ném ra (VD: Quá hạn lịch) -> Trả về 400
    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<?> handleScheduleException(ScheduleException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Bad Request");
        errors.put("message", ex.getMessage()); 
        
        return ResponseEntity.badRequest().body(errors);
    }

    // (Bạn có thể viết thêm các CustomException khác ở đây như UserNotFoundException...)

    // 2. Chốt chặn cuối cùng: Bắt các bug hệ thống (NullPointer, lỗi DB...) -> Trả về 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace(); // In ra console để Backend tự fix bug
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.put("error", "Internal Server Error");
        errors.put("message", "Đã xảy ra lỗi hệ thống, vui lòng thử lại sau!"); 
        // Lưu ý: Không trả ex.getMessage() của lỗi 500 ra cho Client để bảo mật thông tin hệ thống
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}