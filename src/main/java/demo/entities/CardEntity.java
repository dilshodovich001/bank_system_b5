package demo.entities;

import demo.enums.CardType;
import demo.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;


public class CardEntity {
    private UUID id;
    private String cardNumber;
    private String expDate;
    private Status status;
    private Double balance;
    private CardType type;
    private UUID userId;
    private LocalDateTime createdDate;


    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public Status getStatus() {
        return status;
    }

    public Double getBalance() {
        return balance;
    }

    public CardType getType() {
        return type;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String writeCard() {
        return String.format("%s#%s#%s#%s#%s#%s#%s#%s", id, cardNumber, expDate, status, balance, type, userId, createdDate);
    }
}
