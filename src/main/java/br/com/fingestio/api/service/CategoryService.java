package br.com.fingestio.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.fingestio.api.model.Category;
import br.com.fingestio.api.model.User;
import br.com.fingestio.api.dto.category.CreateRequest;
import br.com.fingestio.api.dto.category.UpdateRequest;
import br.com.fingestio.api.repository.CategoryRepository;
import br.com.fingestio.api.repository.UserRepository;
import java.util.List;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public List<Category> getCategoriesByTypeAndUserId(Integer type, Long userId) {
        return categoryRepository.findAll().stream()
            .filter(category -> category.getType() == type && 
                (category.getUserId() == null || category.getUserId().equals(userId)))
            .toList();
    }

    public Category create(CreateRequest createRequest) throws Exception {
        Long userId = createRequest.getUserId();
        if (userId == null) {
            throw new Exception("ID do usuário não pode ser nulo");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }
        
        User user = userOptional.get();
        
        Category category = new Category();
        category.setName(createRequest.getName());
        category.setDescription(createRequest.getDescription());
        category.setType(createRequest.getType());
        category.setUserId(user.getId());
        category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        category.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        
        return categoryRepository.save(category);
    }

    public Category update(Long categoryId, UpdateRequest updateRequest) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            throw new Exception("Categoria não encontrada");
        }
        
        Category category = categoryOptional.get();

        if(category.getUserId() == null) {
            throw new Exception("Categoria padrão não pode ser editada");
        }
        
        category.setName(updateRequest.getName());
        category.setDescription(updateRequest.getDescription());
        category.setType(updateRequest.getType());
        category.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        
        return categoryRepository.save(category);
    }
    
    public void delete(Long categoryId) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            throw new Exception("Categoria não encontrada");
        }
        
        Category category = categoryOptional.get();

        if(category.getUserId() == null) {
            throw new Exception("Categoria padrão não pode ser deletada");
        }
        
        categoryRepository.delete(category);
    }
}