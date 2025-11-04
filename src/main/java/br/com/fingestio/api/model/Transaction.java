package br.com.fingestio.api.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String description;
    private Double amount;
    private String currency;
    @Column(name = "occurred_at")
    private Date occurredAt;
    @Column(name = "due_date")
    private Date dueDate;
    @Column(name = "is_recurring")
    private String isRecurring;
    @Column(name = "is_paid")
    private String isPaid;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private Long userId;

    private Long cardId;

    private Long categoryId;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Date getOccurredAt() {
        return occurredAt;
    }
    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getIsRecurring() {
        return isRecurring;
    }
    public void setIsRecurring(String isRecurring) {
        this.isRecurring = isRecurring;
    }
    public String getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCardId() {
        return cardId;
    }
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}