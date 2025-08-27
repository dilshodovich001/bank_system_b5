package demo.repository;

import demo.entities.UserEntity;
import demo.enums.Role;
import demo.enums.Status;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// bu aslida realni loyida interface lar boladi
public class UserRepository {
    private final File file = new File("users.txt");

    public UserRepository() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void save(List<UserEntity> entities, boolean insert) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, insert))) {
            if (insert) {
                writer.println(entities.getFirst().writeUser());
            } else {
                entities.forEach(entity -> writer.println(entity.writeUser()));
            }
        } catch (IOException e) {
            System.out.println("Writeda error bo`ldi");
        }
    }

    public List<UserEntity> readData() {
        List<UserEntity> temp = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String response = null;
            while ((response = reader.readLine()) != null) {
                String[] split = response.split("#");
                UserEntity entity = new UserEntity();
                entity.setId(UUID.fromString(split[0]));
                entity.setName(split[1]);
                entity.setSurname(split[2]);
                entity.setPhone(split[3]);
                entity.setPassword(split[4]);
                entity.setAge(Integer.parseInt(split[5]));
                entity.setStatus(Status.valueOf(split[6]));
                entity.setRole(Role.valueOf(split[7]));
                entity.setCreatedDate(LocalDateTime.parse(split[8]));
//                entity.setChatId(split[9]);
                temp.add(entity);
            }
        } catch (IOException e) {
            System.out.println("di");
        }
        return temp;
    }
}
