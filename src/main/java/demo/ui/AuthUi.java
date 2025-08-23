package demo.ui;


import demo.controller.AuthController;
import demo.dto.AuthRequest;
import demo.entities.UserEntity;
import demo.enums.Role;
import demo.service.AuthService;
import demo.util.ScannerUtil;

public class AuthUi {
    private final AuthController authController = new AuthController();
    private final AdminUi adminUi = new AdminUi();
    private final UserUi userUi = new UserUi();

    public void start() {
        new AuthService().initAdmin();
        while (true) {
            int n = menu();
            switch (n) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void login() {
        System.out.print("Enter phone : ");
        String phone = ScannerUtil.STRING.next();
        System.out.print("Enter password : ");
        String password = ScannerUtil.STRING.next();
        UserEntity user = authController.login(phone, password);
        if (user.getRole().equals(Role.ADMIN)) {
            adminUi.start(user);
        } else {
            userUi.start(user);
        }
    }

    private void register() {
        System.out.print("Enter name : ");
        String name = ScannerUtil.STRING.next();
        System.out.print("Enter surname : ");
        String surname = ScannerUtil.STRING.next();
        System.out.print("Enter phone : ");
        String phone = ScannerUtil.STRING.next();
        System.out.print("Enter password : ");
        String password = ScannerUtil.STRING.next();
        System.out.print("Enter age : ");
        Integer age = ScannerUtil.NUMBER.nextInt();
        AuthRequest request = new AuthRequest(
                name,
                surname,
                phone,
                password,
                age, null);
        String code = authController.register(request);
        System.out.println(code);
        System.out.print("Enter code : ");
        String c = ScannerUtil.STRING.next();
        if (!code.equals(c)) {
            //removeIf
            return;
        }
        confirm(c, phone);
    }

    public void confirm(String code, String phone) {
        boolean b = authController.confirm(code, phone);
        if (b) System.out.println("Success confirm");
        else System.out.println("Sms code xato");
    }

    private int menu() {
        System.out.println("1.Login");
        System.out.println("2.Register");
        System.out.println("0.Exit");
        System.out.print("Enter menu : ");
        return ScannerUtil.NUMBER.nextInt();
    }
}
