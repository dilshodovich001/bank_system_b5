package demo.service;


import demo.entities.CardEntity;
import demo.enums.CardType;
import demo.enums.Status;
import demo.repository.CardRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CardService {
    private final CardRepository repository = new CardRepository();

    public String createCard(CardType type, UUID userId) {
        LocalDate date = LocalDate.now().plusYears(6);
        String temp = String.valueOf(date);
        String[] split = temp.split("-");
        String year = split[0].substring(2);
        String moth = split[1];
        String expDate = moth + "/" + year;

        String code = CardType.values()[type.ordinal()].getCode();
        String cardNumber = generateUniqueNumber(code);
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(UUID.randomUUID());
        cardEntity.setCardNumber(cardNumber);
        cardEntity.setExpDate(expDate);
        cardEntity.setStatus(Status.NOT_ACTIVE);
        cardEntity.setBalance(0.0);
        cardEntity.setType(type);
        cardEntity.setUserId(userId);
        cardEntity.setCreatedDate(LocalDateTime.now());
        repository.save(List.of(cardEntity),true);
        return cardEntity.getCardNumber();
    }

    public static String generateUniqueNumber(String code) {
        return code + String.valueOf(System.currentTimeMillis()).substring(1);
    }

}
