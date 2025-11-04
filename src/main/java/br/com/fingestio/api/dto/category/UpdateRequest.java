package br.com.fingestio.api.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateRequest {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String name;
    
    @NotBlank(message = "Descrição da categoria é obrigatória")
    private String description;

    @NotNull(message = "Tipo da categoria é obrigatório")
    private Integer type;

    // Constructors
    public UpdateRequest() {}
    
    public UpdateRequest(String name, String description, Integer type) {
        this.name = name;
        this.description = description;
        this.type = type;
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
}