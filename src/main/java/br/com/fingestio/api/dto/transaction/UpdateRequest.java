package br.com.fingestio.api.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class UpdateRequest {

    @NotBlank(message = "Descrição da transação é obrigatória")
    @Size(max = 255, message = "Descrição não pode ter mais de 255 caracteres")
    private String description;

    @NotNull(message = "Valor da transação é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private Double amount;

    @NotBlank(message = "Moeda é obrigatória")
    @Size(max = 10, message = "Código da moeda não pode ter mais de 10 caracteres")
    private String currency;

    @NotNull(message = "User ID é obrigatório")
    private Long userId;

    private Long cardId;

    @NotNull(message = "Category ID é obrigatório")
    private Long categoryId;

    @NotNull(message = "Data de ocorrência é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date occurredAt;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @NotNull(message = "Status de recorrência é obrigatório")
    private Boolean isRecurring;

    @NotNull(message = "Status de pagamento é obrigatório")
    private Boolean isPaid;

    // Constructors
    public UpdateRequest() {}

    public UpdateRequest(String description, Double amount, String currency, 
                        Long userId, Long categoryId, Date occurredAt, 
                        Boolean isRecurring, Boolean isPaid) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.userId = userId;
        this.categoryId = categoryId;
        this.occurredAt = occurredAt;
        this.isRecurring = isRecurring;
        this.isPaid = isPaid;
    }

    // Getters and Setters
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

    public Boolean getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

}