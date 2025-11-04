package br.com.fingestio.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String code;
    private T data;
    private LocalDateTime timestamp;

    // Construtores
    private ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    private ApiResponse(boolean success, String message, String code, T data) {
        this();
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    // Métodos estáticos para criar respostas de sucesso
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operação realizada com sucesso", null, data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, null, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null, null);
    }

    // Métodos estáticos para criar respostas de erro
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, null);
    }

    public static <T> ApiResponse<T> error(String message, ErrorCode errorCode) {
        return new ApiResponse<>(false, message, errorCode.getCode(), null);
    }

    public static <T> ApiResponse<T> error(String message, ErrorCode errorCode, T data) {
        return new ApiResponse<>(false, message, errorCode.getCode(), data);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}