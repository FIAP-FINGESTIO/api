package br.com.fingestio.api.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        String message;
        if (ex.getMessage().contains("Required request body is missing")) {
            message = "Request body é obrigatório. Por favor, forneça um JSON válido com os dados necessários.";
        } else {
            message = "Formato de JSON inválido. Verifique a sintaxe do JSON enviado.";
        }
        
        ApiResponse<String> response = ApiResponse.error(message, ErrorCode.MISSING_REQUEST_BODY);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ValidationErrorResponse response = new ValidationErrorResponse(
            "Erro de validação nos dados enviados",
            ErrorCode.VALIDATION_ERROR,
            errors
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingParameter(MissingServletRequestParameterException ex) {
        ApiResponse<String> response = ApiResponse.error(
            "Parâmetro obrigatório ausente: " + ex.getParameterName(),
            ErrorCode.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ApiResponse<String> response = ApiResponse.error(
            "Tipo de dado inválido para o parâmetro: " + ex.getName(),
            ErrorCode.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        System.out.println("Erro não tratado: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        ApiResponse<String> response = ApiResponse.error(
            "Ocorreu um erro interno no servidor",
            ErrorCode.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.internalServerError().body(response);
    }
}