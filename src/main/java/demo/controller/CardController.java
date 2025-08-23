package demo.controller;


import demo.enums.CardType;
import demo.service.CardService;

import java.util.UUID;

public class CardController {
    private CardService cardService = new CardService();

    public String createCard(CardType type, UUID userId) {
        return cardService.createCard(type,userId);
    }
}
