package br.com.fingestio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fingestio.api.model.Category;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM categories WHERE user_id = :userId OR user_id IS NULL ORDER BY name", nativeQuery = true)
    List<Category> findByUserId(@Param("userId") Long userId);
}