package demo.ui;


import demo.entities.UserEntity;
import demo.util.ScannerUtil;

public class AdminUi {
    private final CardUi cardUi = new CardUi();

    public void start(UserEntity user) {
        while (true) {
            switch (adminMenu()) {
                case 1 -> cardUi.start(user);
                case 0 -> {
                    return;
                }
            }
        }
    }

    public int adminMenu() {
        System.out.println("1.Card");
        System.out.println("2.Terminals");
        System.out.println("3.Profile");
        System.out.println("4.Transaction");
        System.out.println("5.Statistic");
        System.out.println("0.Exit");
        System.out.print("Enter Number: ");
        return ScannerUtil.NUMBER.nextInt();
    }

}
