package demo.ui;


import demo.controller.CardController;
import demo.entities.UserEntity;
import demo.enums.CardType;
import demo.util.ScannerUtil;

public class CardUi {
    private final CardController cardController = new CardController();

    public void start(UserEntity user) {
        while (true) {
            switch (cardMenu()) {
                case 1 -> createCard(user);
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void createCard(UserEntity user) {
        for (int i = 0; i < CardType.values().length; i++) {
            System.out.println(i + 1 + "." + CardType.values()[i]);
        }
        System.out.print("Card typeni tanla ");
        int index = ScannerUtil.NUMBER.nextInt() - 1;
        CardType type = CardType.values()[index];
        String response = cardController.createCard(
                type, user.getId()
        );
        System.out.println(response);
    }

    public int cardMenu() {
        System.out.println("1.Create Card");
        System.out.println("2.Card List");
        System.out.println("3.Update Card");
        System.out.println("4.Change Card Status");
        System.out.println("5.Delete Card");
        System.out.println("0.Exit");
        System.out.print("Enter number: ");
        return ScannerUtil.NUMBER.nextInt();
    }

}
