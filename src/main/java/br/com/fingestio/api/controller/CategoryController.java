package br.com.fingestio.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fingestio.api.service.CategoryService;
import br.com.fingestio.api.dto.category.CreateRequest;
import br.com.fingestio.api.dto.category.UpdateRequest;
import br.com.fingestio.api.model.Category;
import br.com.fingestio.api.utils.ApiResponse;
import br.com.fingestio.api.utils.ErrorCode;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/all/{user_id}")
    public ResponseEntity<ApiResponse<List<Category>>> getCategoriesByUserId(@PathVariable Long user_id) {
        try {
            List<Category> categories = categoryService.getCategoriesByUserId(user_id);
            ApiResponse<List<Category>> response = ApiResponse.success("Categorias encontradas com sucesso", categories);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            ApiResponse<List<Category>> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{type}/{user_id}")
    public ResponseEntity<ApiResponse<List<Category>>> getCategoriesByTypeAndUserId(@PathVariable Integer type, @PathVariable Long user_id) {
        try {
            List<Category> categories = categoryService.getCategoriesByTypeAndUserId(type, user_id);
            ApiResponse<List<Category>> response = ApiResponse.success("Categorias encontradas com sucesso", categories);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            ApiResponse<List<Category>> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Category>> create(@Valid @RequestBody CreateRequest createRequest) {
        try {
            Category category = categoryService.create(createRequest);
            ApiResponse<Category> response = ApiResponse.success("Categoria criada com sucesso", category);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("TESTE");
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            ApiResponse<Category> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable Long id, @Valid @RequestBody UpdateRequest updateRequest) {
        try {
            Category category = categoryService.update(id, updateRequest);
            ApiResponse<Category> response = ApiResponse.success("Categoria atualizada com sucesso", category);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode;
            if (e.getMessage().contains("não encontrada")) {
                errorCode = ErrorCode.NOT_FOUND;
            } else {
                errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            }
            ApiResponse<Category> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            ApiResponse<String> response = ApiResponse.success("Categoria excluída com sucesso", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode;
            if (e.getMessage().contains("não encontrada")) {
                errorCode = ErrorCode.NOT_FOUND;
            } else {
                errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            }
            ApiResponse<String> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}