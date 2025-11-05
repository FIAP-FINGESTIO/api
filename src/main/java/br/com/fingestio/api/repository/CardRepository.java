package br.com.fingestio.api.repository;

import br.com.fingestio.api.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    // Se a entidade tem ownerId, usar ownerId
    List<Card> findByOwnerId(Long ownerId);
    
    List<Card> findByOwnerIdAndAlias(Long ownerId, String alias);
    
    List<Card> findByIssuer(String issuer);
    
    List<Card> findByShared(String shared);
}