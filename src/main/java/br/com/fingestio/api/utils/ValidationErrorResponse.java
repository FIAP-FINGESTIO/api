package br.com.fingestio.api.utils;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {
    private boolean success;
    private String message;
    private String code;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(String message, ErrorCode errorCode, Map<String, String> errors) {
        this.success = false;
        this.message = message;
        this.code = errorCode.getCode();
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    // Getters e Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}