package br.com.fingestio.api.service;

import br.com.fingestio.api.dto.card.CreateRequest;
import br.com.fingestio.api.dto.card.UpdateRequest;
import br.com.fingestio.api.model.Card;
import br.com.fingestio.api.model.User;
import br.com.fingestio.api.repository.CardRepository;
import br.com.fingestio.api.repository.UserRepository;
import br.com.fingestio.api.service.CardService;
import br.com.fingestio.api.utils.Global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CardService{

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Card> getCardsByUserId(Long userId) {
        return cardRepository.findByUserId(userId);
    }

    public Card create(CreateRequest createRequest) {
        Long userId = createRequest.getUserId();
        if (userId == null) {
            throw new RuntimeException("ID do usuário não pode ser nulo");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        List<Card> existingCards = cardRepository.findByUserId( createRequest.getUserId());
        if (!existingCards.isEmpty()) {
            throw new RuntimeException("Já existe um cartão com este nome para o usuário");
        }

        Card card = new Card();
        card.setIssuer(createRequest.getIssuer());
        card.setAlias(createRequest.getAlias());
        card.setLastFourDigits(createRequest.getLastFourDigits());
        String shared = Global.convertBooleanStringToYesNo(createRequest.getShared());
        card.setShared(shared);
        card.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        card.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return cardRepository.save(card);
    }

    public Card update(Long id, UpdateRequest updateRequest) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (!cardOptional.isPresent()) {
            throw new RuntimeException("Cartão não encontrado");
        }

        Card card = cardOptional.get();

        if (updateRequest.getAlias() != null && !updateRequest.getAlias().equals(card.getAlias())) {
            List<Card> existingCards = cardRepository.findByUserIdAndAlias(
                card.getUserId(), 
                updateRequest.getAlias()
            );
            if (!existingCards.isEmpty()) {
                throw new RuntimeException("Já existe um cartão com este nome para o usuário");
            }
        }

        if (updateRequest.getAlias() != null) {
            card.setAlias(updateRequest.getAlias());
        }
        if (updateRequest.getLastFourDigits() != null) {
            card.setLastFourDigits(updateRequest.getLastFourDigits());
        }
        if (updateRequest.getShared() != null) {
            String shared = Global.convertBooleanStringToYesNo(updateRequest.getShared());

            card.setShared(shared);
        }
        if(updateRequest.getIssuer() != null) {
            card.setIssuer(updateRequest.getIssuer());
        }
        
        card.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return cardRepository.save(card);
    }

    public void delete(Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (!cardOptional.isPresent()) {
            throw new RuntimeException("Cartão não encontrado");
        }

        Card card = cardOptional.get();

        cardRepository.delete(card);
    }
}