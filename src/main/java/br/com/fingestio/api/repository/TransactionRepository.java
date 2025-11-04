package br.com.fingestio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fingestio.api.model.Transaction;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    @Query(value = """
            SELECT t.* FROM transactions t
            LEFT JOIN categories c ON t.category_id = c.id
            WHERE (CASE WHEN :userId IS NULL THEN 1 ELSE CASE WHEN t.user_id = :userId THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :categoryId IS NULL THEN 1 ELSE CASE WHEN t.category_id = :categoryId THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :cardId IS NULL THEN 1 ELSE CASE WHEN t.card_id = :cardId THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :description IS NULL THEN 1 ELSE CASE WHEN LOWER(t.description) LIKE LOWER('%' || :description || '%') THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :minAmount IS NULL THEN 1 ELSE CASE WHEN t.amount >= :minAmount THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :maxAmount IS NULL THEN 1 ELSE CASE WHEN t.amount <= :maxAmount THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :startDate IS NULL THEN 1 ELSE CASE WHEN t.occurred_at >= :startDate THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :endDate IS NULL THEN 1 ELSE CASE WHEN t.occurred_at <= :endDate THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :isPaid IS NULL THEN 1 ELSE CASE WHEN t.is_paid = :isPaid THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :isRecurring IS NULL THEN 1 ELSE CASE WHEN t.is_recurring = :isRecurring THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :currency IS NULL THEN 1 ELSE CASE WHEN t.currency = :currency THEN 1 ELSE 0 END END) = 1
            AND (CASE WHEN :type IS NULL THEN 1 ELSE CASE WHEN c.type = :type THEN 1 ELSE 0 END END) = 1
            ORDER BY t.occurred_at DESC
            """, nativeQuery = true)
    List<Transaction> searchTransactions(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("cardId") Long cardId,
            @Param("description") String description,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("isPaid") String isPaid,
            @Param("isRecurring") String isRecurring,
            @Param("currency") String currency,
            @Param("type") Integer type);
}