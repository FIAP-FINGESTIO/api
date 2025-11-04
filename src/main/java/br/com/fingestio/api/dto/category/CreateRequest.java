package br.com.fingestio.api.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateRequest {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String name;
    
    @NotBlank(message = "Descrição da categoria é obrigatória")
    private String description;

    @NotNull(message = "Tipo da categoria é obrigatório")
    private Integer type;

    @NotNull(message = "User ID é obrigatório")
    private Long userId;

    // Constructors
    public CreateRequest() {}
    
    public CreateRequest(String name, String description, Integer type, Long userId) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.userId = userId;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}