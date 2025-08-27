package demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserCardEntity {
    private UUID id;
    private UserEntity user;
    private CardEntity card;
    private LocalDateTime created;

    public UserCardEntity(UserEntity user, CardEntity card) {
        id = UUID.randomUUID();
        this.user = user;
        this.card = card;
        this.created = LocalDateTime.now();
    }
}
