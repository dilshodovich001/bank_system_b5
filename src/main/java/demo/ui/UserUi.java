package demo.ui;


import demo.controller.UserController;
import demo.entities.UserEntity;
import demo.util.ScannerUtil;

public class UserUi {
    private final UserController userController = new UserController();

    public void start(UserEntity user) {
        while (true) {
            switch (menu()) {
                case 1 -> addCard(user);
//                case 2 ->
//                case 3 ->
//                case 4 ->
//                case 5 ->
                case 6 -> {
                    return;
                }
            }
        }
    }

    private void addCard(UserEntity user) {
        System.out.print("Enter card Number : ");
        String cardNumber = ScannerUtil.STRING.next();
        boolean response = userController.addCard(user, cardNumber);
        System.out.println(response);
    }

    public int menu() {
        System.out.println("1.Add card");
        System.out.println("2.Show card");
        System.out.println("3.Change card status");
        System.out.println("4.Delete card");
        System.out.println("5.Refill");
        System.out.println("6.Transaction");
        System.out.println("7.Make payment");
        return ScannerUtil.NUMBER.nextInt();
    }
}
