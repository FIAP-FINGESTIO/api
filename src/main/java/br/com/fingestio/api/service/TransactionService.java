package br.com.fingestio.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fingestio.api.repository.CardRepository;
import br.com.fingestio.api.repository.CategoryRepository;
import br.com.fingestio.api.repository.TransactionRepository;
import br.com.fingestio.api.repository.UserRepository;
import br.com.fingestio.api.utils.Global;
import br.com.fingestio.api.dto.transaction.UpdateRequest;
import br.com.fingestio.api.model.Card;
import br.com.fingestio.api.model.Category;
import br.com.fingestio.api.model.Transaction;
import br.com.fingestio.api.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CardRepository cardRepository;

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
                Optional<User> userOptional = userRepository.findById(createRequest.getUserId());
                if (!userOptional.isPresent()) {
                    throw new RuntimeException("Usuário não encontrado");
                }
        
                Optional<Category> categoryOptional = categoryRepository.findById(createRequest.getCategoryId());
                if (!categoryOptional.isPresent()) {
                    throw new RuntimeException("Categoria não encontrada");
                }
        
                if (createRequest.getCardId() != null) {
                    Optional<Card> cardOptional = cardRepository.findById(createRequest.getCardId());
                    if (!cardOptional.isPresent()) {
                        throw new RuntimeException("Cartão não encontrado");
                    }
                    
                    Card card = cardOptional.get();
                    if (!card.getUserId().equals(createRequest.getUserId())) {
                        throw new RuntimeException("O cartão não pertence ao usuário informado");
                    }
                }
        
                Transaction transaction = new Transaction();
                transaction.setDescription(createRequest.getDescription());
                transaction.setAmount(createRequest.getAmount());
                transaction.setCurrency(createRequest.getCurrency());
                transaction.setUserId(createRequest.getUserId());
                transaction.setCategoryId(createRequest.getCategoryId());
                transaction.setCardId(createRequest.getCardId());
                transaction.setOccurredAt(createRequest.getOccurredAt());
                transaction.setDueDate(createRequest.getDueDate());
                
                transaction.setIsRecurring(Global.convertBooleanStringToYesNo(createRequest.getIsRecurring()));
                transaction.setIsPaid(Global.convertBooleanStringToYesNo(createRequest.getIsPaid()));
                
                transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                transaction.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
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
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (!transactionOptional.isPresent()) {
            throw new RuntimeException("Transação não encontrada");
        }

        Transaction transaction = transactionOptional.get();

        if (updateRequest.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(updateRequest.getCategoryId());
            if (!categoryOptional.isPresent()) {
                throw new RuntimeException("Categoria não encontrada");
            }
        }

        if (updateRequest.getCardId() != null) {
            Optional<Card> cardOptional = cardRepository.findById(updateRequest.getCardId());
            if (!cardOptional.isPresent()) {
                throw new RuntimeException("Cartão não encontrado");
            }
            
            Card card = cardOptional.get();
            if (!card.getUserId().equals(transaction.getUserId())) {
                throw new RuntimeException("O cartão não pertence ao usuário da transação");
            }
        }
        if (updateRequest.getDescription() != null) {
            transaction.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getAmount() != null) {
            transaction.setAmount(updateRequest.getAmount());
        }
        if (updateRequest.getCurrency() != null) {
            transaction.setCurrency(updateRequest.getCurrency());
        }
        if (updateRequest.getCategoryId() != null) {
            transaction.setCategoryId(updateRequest.getCategoryId());
        }
        if (updateRequest.getCardId() != null) {
            transaction.setCardId(updateRequest.getCardId());
        }
        if (updateRequest.getOccurredAt() != null) {
            transaction.setOccurredAt(updateRequest.getOccurredAt());
        }
        if (updateRequest.getDueDate() != null) {
            transaction.setDueDate(updateRequest.getDueDate());
        }
        if (updateRequest.getIsRecurring() != null) {
            transaction.setIsRecurring(Global.convertBooleanStringToYesNo(updateRequest.getIsRecurring()));
        }
        if (updateRequest.getIsPaid() != null) {
            transaction.setIsPaid(Global.convertBooleanStringToYesNo(updateRequest.getIsPaid()));
        }
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) throws Exception {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new Exception("Transação não encontrada"));
        transactionRepository.delete(transaction);
    }
}