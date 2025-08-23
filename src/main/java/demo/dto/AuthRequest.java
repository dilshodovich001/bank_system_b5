package demo.dto;

public class AuthRequest {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private Integer age;
    private Long chatId;

    public AuthRequest(String name, String surname, String phone, String password, Integer age,Long chatId) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
        this.age = age;
        this.chatId = chatId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
