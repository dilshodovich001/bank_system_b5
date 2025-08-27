package demo.entities;


import demo.enums.Role;
import demo.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
@Getter
@Setter
public class UserEntity {
    private UUID id;
    private String name;
    private String surname;
    private Integer age;
    private String password;
    private String phone;
    private Role role;
    private Status status;
    private LocalDateTime createdDate;
    private String chatId;
//    private ArrayList<CardEntity> cardEntities;  old version

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String writeUser() {
        return String.format("%s#%s#%s#%s#%s#%s#%s#%s#%s#%s",
                id,
                name,
                surname,
                phone,
                password,
                age,
                status,
                role,
                createdDate,
                chatId);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}
