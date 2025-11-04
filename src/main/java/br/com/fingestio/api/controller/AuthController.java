package br.com.fingestio.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fingestio.api.service.AuthService;
import br.com.fingestio.api.dto.auth.LoginRequest;
import br.com.fingestio.api.model.User;
import br.com.fingestio.api.utils.ApiResponse;
import br.com.fingestio.api.utils.ErrorCode;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.doLogin(loginRequest.getEmail(), loginRequest.getPassword());
            ApiResponse<User> response = ApiResponse.success("Login realizado com sucesso", user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode;
            if (e.getMessage().contains("não encontrado")) {
                errorCode = ErrorCode.NOT_FOUND;
            } else if (e.getMessage().contains("Senha incorreta")) {
                errorCode = ErrorCode.INVALID_PASSWORD;
            } else {
                errorCode = ErrorCode.AUTHENTICATION_ERROR;
            }
            
            ApiResponse<User> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}