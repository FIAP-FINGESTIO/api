package br.com.fingestio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fingestio.api.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}