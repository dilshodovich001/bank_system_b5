package demo.repository;



import demo.entities.CardEntity;
import demo.enums.CardType;
import demo.enums.Status;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// bu aslida realni loyida interface lar boladi
public class CardRepository {
    private final File file = new File("cards.txt");

    public CardRepository() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void save(List<CardEntity> entities, boolean insert) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, insert))) {
            if (insert) {
                writer.println(entities.getFirst().writeCard());
            } else {
                entities.forEach(entity -> writer.println(entity.writeCard()));
            }
        } catch (IOException e) {
            System.out.println("Writeda error bo`ldi");
        }
    }

    public List<CardEntity> readData() {
        List<CardEntity> temp = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String response = null;
            while ((response = reader.readLine()) != null) {
                String[] split = response.split("#");
                CardEntity entity = new CardEntity();
                entity.setId(UUID.fromString(split[0]));
                entity.setCardNumber(split[1]);
                entity.setExpDate(split[2]);
                entity.setStatus(Status.valueOf(split[3]));
                entity.setBalance(Double.parseDouble(split[4]));
                entity.setType(CardType.valueOf(split[5]));
                entity.setUserId(UUID.fromString(split[6]));
                entity.setCreatedDate(LocalDateTime.parse(split[7]));
                temp.add(entity);
            }
        } catch (IOException e) {
            System.out.println("card read error");
        }
        return temp;
    }
}
