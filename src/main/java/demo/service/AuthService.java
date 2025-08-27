package demo.service;


import demo.domain.HasSmsCodeGenerator;
import demo.dto.AuthRequest;
import demo.entities.UserEntity;
import demo.enums.Role;
import demo.enums.Status;
import demo.exceptions.UserException;
import demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

public class AuthService implements HasSmsCodeGenerator {
    private final Map<String, String> smsMap = new HashMap<>();
    private final UserRepository repository = new UserRepository();

    public String register(AuthRequest request, boolean isBot) {
        ///check
        check(request);
        UserEntity user = getUserByPhone(request.getPhone());
        if (user != null) {
            return "User already registered";
        }
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(request.getName());
        entity.setSurname(request.getSurname());
        entity.setPassword(request.getPassword());
        entity.setPhone(request.getPhone());
        entity.setAge(request.getAge());
        entity.setRole(Role.USER);
        entity.setStatus(isBot ? Status.ACTIVE : Status.NOT_ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
//        entity.setChatId(request.getChatId().toString());

        repository.save(List.of(entity), true);
        Integer code = generatedCode();
        smsMap.put(entity.getPhone(), String.valueOf(code));

        return String.valueOf(code);
    }

    public void check(AuthRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new UserException("Name is invalid");
        }
        if (request.getSurname() == null || request.getSurname().isBlank()) {
            throw new UserException("Surname is invalid");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new UserException("Password is invalid");
        }
        if (request.getPhone() == null || request.getPhone().isBlank()) {
            throw new UserException("Phone is invalid");
        }
        if (request.getAge() == null || request.getAge() <= 18) {
            throw new UserException("Age is invalid");
        }
    }

    public UserEntity getUserByPhone(String phone) {
        for (UserEntity entity : repository.readData()) {
            if (entity.getPhone().equals(phone)) {
                return entity;
            }
        }
        return null;
    }

    public boolean confirm(String code, String phone) {
        List<UserEntity> userEntities = repository.readData();
        UserEntity user = userEntities
                .stream()
                .filter(u -> u.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (smsMap.get(user.getPhone()).equals(code)) {
            user.setStatus(Status.ACTIVE);
            repository.save(userEntities, false);
            return true;
        } else {
            return false;
        }
    }


    public void initAdmin() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName("Admin");
        userEntity.setSurname("Adminov");
        userEntity.setAge(20);
        userEntity.setPhone("1111");
        userEntity.setPassword("1111");
        userEntity.setRole(Role.valueOf("ADMIN"));
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setStatus(Status.ACTIVE);
        Optional<UserEntity> first = repository.readData().stream().filter(s -> s.getPhone().equals(userEntity.getPhone())).findFirst();
        if (first.isEmpty()) {
            repository.save(List.of(userEntity), true);
        }
    }

    public UserEntity login(String phone, String password) {
        return repository.readData()
                .stream()
                .filter(u -> u.getPhone().equals(phone) && u.getPassword().equals(password))
                .findFirst().orElseThrow(() -> new UserException("User not found"));
    }

    public String updateName(String phone, String name) {
        List<UserEntity> userEntities = repository.readData();
        UserEntity user = userEntities.stream()
                .filter(u -> u.getPhone().equals(phone))
                .findFirst().orElse(null);
        if (user == null) {
            return "404";
        } else {
            user.setName(name);
            repository.save(userEntities, false);
        }
        return "200";
    }

    public String getUserByChatId(String chatId) {
        Optional<UserEntity> user = repository.readData()
                .stream()
                .filter(u -> u.getChatId().equals(chatId))
                .findFirst();
        if (user.isPresent()) {
            return user.get().getPhone();
        }
        return "404";
    }
}
