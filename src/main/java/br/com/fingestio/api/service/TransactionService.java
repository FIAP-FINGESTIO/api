package br.com.fingestio.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.fingestio.api.repository.TransactionRepository;
import br.com.fingestio.api.utils.Global;
import br.com.fingestio.api.dto.transaction.UpdateRequest;
import br.com.fingestio.api.model.Transaction;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> searchTransactions(Long userId, Long categoryId, Long cardId,
            String description, Double minAmount, Double maxAmount,
            Date startDate, Date endDate, String isPaid,
            String isRecurring, String currency, Integer type) {
        return transactionRepository.searchTransactions(userId, categoryId, cardId,
                description, minAmount, maxAmount,
                startDate, endDate, isPaid,
                isRecurring, currency, type);
    }

    public Transaction createTransaction(br.com.fingestio.api.dto.transaction.CreateRequest createRequest)
            throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDescription(createRequest.getDescription());
        transaction.setAmount(createRequest.getAmount());
        transaction.setCurrency(createRequest.getCurrency());
        transaction.setUserId(createRequest.getUserId());
        transaction.setCategoryId(createRequest.getCategoryId());
        transaction.setCardId(createRequest.getCardId());
        transaction.setOccurredAt(new java.sql.Timestamp(createRequest.getOccurredAt().getTime()));
        transaction.setDueDate(
                createRequest.getDueDate() != null ? new java.sql.Timestamp(createRequest.getDueDate().getTime())
                        : new java.sql.Timestamp(createRequest.getOccurredAt().getTime()));
        transaction.setIsRecurring(Global.convertBooleanStringToYesNo(createRequest.getIsRecurring()));
        transaction.setIsPaid(Global.convertBooleanStringToYesNo(createRequest.getIsPaid()));
        transaction.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        transaction.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return transactionRepository.save(transaction);
    }

    public Transaction payTransaction(Long transactionId) throws Exception {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new Exception("Transação não encontrada"));
        transaction.setIsPaid("Y");
        transaction.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionId, UpdateRequest updateRequest) throws Exception {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new Exception("Transação não encontrada"));
        transaction.setDescription(updateRequest.getDescription());
        transaction.setAmount(updateRequest.getAmount());
        transaction.setCurrency(updateRequest.getCurrency());
        transaction.setCategoryId(updateRequest.getCategoryId());
        transaction.setCardId(updateRequest.getCardId());
        transaction.setOccurredAt(new java.sql.Timestamp(updateRequest.getOccurredAt().getTime()));
        transaction.setIsRecurring(Global.convertBooleanStringToYesNo(updateRequest.getIsRecurring()));
        transaction.setDueDate(
                updateRequest.getDueDate() != null ? new java.sql.Timestamp(updateRequest.getDueDate().getTime())
                        : new java.sql.Timestamp(updateRequest.getOccurredAt().getTime()));
        transaction.setIsPaid(Global.convertBooleanStringToYesNo(updateRequest.getIsPaid()));
        transaction.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) throws Exception {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new Exception("Transação não encontrada"));
        transactionRepository.delete(transaction);
    }
}