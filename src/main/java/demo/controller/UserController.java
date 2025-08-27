package demo.controller;

import demo.entities.UserEntity;
import demo.service.UserCardService;

public class UserController {
    private final UserCardService userCardService = new UserCardService();
    public boolean addCard(UserEntity user, String cardNumber) {
        return userCardService.addCard(user,cardNumber);
    }
}
