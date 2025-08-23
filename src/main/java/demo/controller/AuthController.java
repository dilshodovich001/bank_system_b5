package demo.controller;

import demo.dto.AuthRequest;
import demo.entities.UserEntity;
import demo.service.AuthService;

// auth/login
public class AuthController {
    private final AuthService authService = new AuthService();

    public String register(AuthRequest request) {
        return authService.register(request,false);
    }

    public boolean confirm(String code, String phone) {
        return authService.confirm(code,phone);
    }

    public UserEntity login(String phone, String password) {
        return authService.login(phone,password);
    }
}
