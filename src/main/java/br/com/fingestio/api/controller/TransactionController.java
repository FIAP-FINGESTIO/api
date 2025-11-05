package br.com.fingestio.api.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fingestio.api.service.TransactionService;
import br.com.fingestio.api.dto.transaction.CreateRequest;
import br.com.fingestio.api.model.Transaction;
import br.com.fingestio.api.utils.ApiResponse;
import br.com.fingestio.api.utils.ErrorCode;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getTransactionsByUserId(@PathVariable Long user_id) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByUserId(user_id);
            ApiResponse<List<Transaction>> response = ApiResponse.success(transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            ApiResponse<List<Transaction>> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Transaction>>> searchTransactions(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long cardId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) String isPaid,
            @RequestParam(required = false) String isRecurring,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) Integer type)

    {
        try {
            List<Transaction> transactions = transactionService.searchTransactions(
                    userId, categoryId, cardId, description, minAmount, maxAmount,
                    startDate, endDate, isPaid, isRecurring, currency, type);
            ApiResponse<List<Transaction>> response = ApiResponse.success("Busca realizada com sucesso", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            ApiResponse<List<Transaction>> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@Valid @RequestBody CreateRequest createRequest) {
        try {
            Transaction transaction = transactionService.createTransaction(createRequest);
            ApiResponse<Transaction> response = ApiResponse.success("Transação criada com sucesso", transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            ApiResponse<Transaction> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PatchMapping("/{id}/pay")
    public ResponseEntity<ApiResponse<Transaction>> payTransaction(@PathVariable Long id) {
        try {
            Transaction transaction = transactionService.payTransaction(id);
            ApiResponse<Transaction> response = ApiResponse.success("Transação paga com sucesso", transaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode;
            if (e.getMessage().contains("não encontrada")) {
                errorCode = ErrorCode.NOT_FOUND;
            } else {
                errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            }
            ApiResponse<Transaction> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction(@PathVariable Long id, @Valid @RequestBody CreateRequest updateRequest) {
        try {
            Transaction transaction = transactionService.payTransaction(id);
            ApiResponse<Transaction> response = ApiResponse.success("Transação paga com sucesso", transaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode;
            if (e.getMessage().contains("não encontrada")) {
                errorCode = ErrorCode.NOT_FOUND;
            } else {
                errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            }
            ApiResponse<Transaction> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            ApiResponse<Void> response = ApiResponse.success("Transação deletada com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorCode errorCode;
            if (e.getMessage().contains("não encontrada")) {
                errorCode = ErrorCode.NOT_FOUND;
            } else {
                errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            }
            ApiResponse<Void> response = ApiResponse.error(e.getMessage(), errorCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

}