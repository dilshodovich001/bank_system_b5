package demo.service;

import demo.entities.CardEntity;
import demo.entities.UserCardEntity;
import demo.entities.UserEntity;
import demo.exceptions.CardException;
import demo.repository.UserCardRepository;

import java.util.List;

public class UserCardService {
    private final UserCardRepository userCardRepository = new UserCardRepository();
    private final CardService cardService = new CardService();

    public boolean addCard(UserEntity user, String cardNumber) {
        CardEntity cardEntity = cardService.getCardEntityByCardNumber(cardNumber);
        if (cardEntity == null) {
            throw new CardException("Card not found");
        }
        if (findByCardNumber(cardNumber) != null) {
            throw new CardException("Card allaqachon birlashtirilgan");
        }
        UserCardEntity entity = new UserCardEntity(user, cardEntity);
        userCardRepository.saveOrUpdate(List.of(entity), true);
        return true;
    }

    public UserCardEntity findByCardNumber(String cardNumber) {
        return userCardRepository.readData()
                .stream()
                .filter(uc -> uc.getCard().getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);
    }

}
