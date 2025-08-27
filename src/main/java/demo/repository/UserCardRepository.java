package demo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.entities.UserCardEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserCardRepository {
    private final ObjectMapper mapper;
    private final File file;

    public UserCardRepository() {
        file = new File("user_cards.json");
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void saveOrUpdate(List<UserCardEntity> entities, boolean isAppend) {
        List<UserCardEntity> list = readData();
        if (isAppend) {
            list.addAll(entities);
            try {
                mapper.writeValue(file, list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            clear();
            try {
                mapper.writeValue(file,entities);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<UserCardEntity> readData() {
        if (!file.exists() || !file.canRead()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<List<UserCardEntity>>() {
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }


    public void clear() {
        try {
            mapper.writeValue(file, new ArrayList<>());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
