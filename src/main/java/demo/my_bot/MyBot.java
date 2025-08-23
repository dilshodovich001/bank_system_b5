package demo.my_bot;

import demo.dto.AuthRequest;
import demo.service.AuthService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyBot extends TelegramLongPollingBot {
    private final Map<Long, String> step = new HashMap<>();
    private final AuthService authService = new AuthService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long callChatId = update.getCallbackQuery().getMessage().getChatId();
            switch (data) {
                case "name" -> {
                    send(callChatId, "Ismingni kirit");
                    step.put(callChatId, "name_step");
                }
                case "surname" -> {
                }
                case "password" -> {
                }
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            if (text.equals("/start")) {
                send(chatId, "Bu bizning Bank System ilovamizdan foydalanish uchun");
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Contacni yuborish tugamasini bosing");
                sendMessage.setChatId(chatId);

                List<KeyboardRow> buttonList = new LinkedList<>();

                KeyboardRow row_one = new KeyboardRow();
                KeyboardButton button = new KeyboardButton("Telefon nomer yuborish");
                button.setRequestContact(true); // Tugma bosilganda foydalanuvchining telefon raqami kontakt sifatida yuboriladi
                row_one.add(button);

                buttonList.add(row_one);

                ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
                replyMarkup.setKeyboard(buttonList);
                replyMarkup.setResizeKeyboard(true);

                sendMessage.setReplyMarkup(replyMarkup);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (step.containsKey(chatId) && step.get(chatId).equals("name_step")) {
                String newName = update.getMessage().getText();

                // Telefon raqamni oldinroq saqlab qo‘yishingiz kerak
                String phoneNumber = authService.getUserByChatId(chatId.toString()); // Masalan shu tarzda olish mumkin

                String response = authService.updateName(phoneNumber, newName);
                if (response.equals("404")) {
                    send(chatId, "User not found");
                } else {
                    send(chatId, "Ismingiz o‘zgartirildi");
                    step.remove(chatId); // step tugadi
                }
            }
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            // Agar message contact bo'lsa
            if (message.hasContact()) {
                Contact contact = message.getContact(); // message dan kontaktni get qilib olamiz.
                // Kontakt obyektining qiymatlari phoneNumber,firstName,lastName,userId,vCard
                System.out.println("FirstName:" + contact.getFirstName());
                System.out.println("LastName:" + contact.getLastName());
                System.out.println("PhoneNumber:" + contact.getPhoneNumber());
                System.out.println("VCard:" + contact.getVCard());
                System.out.println("UserId:" + contact.getUserId());
                if (!contact.getUserId().equals(message.getChatId())) {
                    send(message.getChatId(), "Mazgi bu ozingni contacting emasku");
                }
                String response = authService.register(new AuthRequest(
                        contact.getFirstName() == null ? "Name Bot" : contact.getFirstName(),
                        contact.getLastName() == null ? "Surname Bot" : contact.getLastName(),
                        contact.getPhoneNumber(),
                        contact.getPhoneNumber(),
                        20,
                        message.getChatId()
                ), true);
                if (response.equals("User already registered")) {
                    send(message.getChatId(), "Siz avval royxatdan otgan ekansiz");
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("Siz royxatdan otdingiz");
                    buttonList(sendMessage);
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public String getBotUsername() {
        return " ";
    }

    public void send(Long chatId, String text) {
        SendMessage mess = new SendMessage();
        mess.setChatId(chatId);
        mess.setText(text);

        try {
            execute(mess);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void buttonList(SendMessage message) {
        message.setText("Siz royxatdan otdingiz hohlaysizmi shu malumotlarni ozgartirishni ? "); // Matn set qilamiz
        message.setChatId(message.getChatId()); // chat id set qilamiz
        List<List<InlineKeyboardButton>> rowList = new LinkedList<>(); //Qatorlar Listini yaratib olamiz (Qatorlar ro'yxatini)
        List<InlineKeyboardButton> row = new LinkedList<>();  // Qator yaratib olamiz.
        // InlineKeyboardButton class sidan obyekt yaratamiz
        // Bu class haqida InlineKeyboardButton maqolasida yanada yaxshiroq tushunchaga ega bo'lasiz
        InlineKeyboardButton nameButton = new InlineKeyboardButton();
        nameButton.setText("Change name");
        nameButton.setCallbackData("name");
        row.add(nameButton);
        InlineKeyboardButton surnameButton = new InlineKeyboardButton();
        surnameButton.setText("Change surname");
        surnameButton.setCallbackData("surname");
        row.add(surnameButton);
        InlineKeyboardButton passwordButton = new InlineKeyboardButton();
        passwordButton.setText("Change password");
        passwordButton.setCallbackData("password");
        row.add(passwordButton); // Qatorga buttonni qo'shib olamiz.
        rowList.add(row);  // Qatorlar ro'yxatiga 1 qatorni qo'shib (row ni qo'shib) olamiz.
        InlineKeyboardMarkup inlineKeyboardMarkub = new InlineKeyboardMarkup();
        inlineKeyboardMarkub.setKeyboard(rowList); // Qatorlar ro'yxatini set qilamiz
        message.setReplyMarkup(inlineKeyboardMarkub); //
        try {
            execute(message); // Tayyorlab olgan message obyektini yuborish.
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    /*List<KeyboardRow> buttonList = new LinkedList<>();

        KeyboardRow row_one = new KeyboardRow();
        KeyboardButton add = new KeyboardButton("Ism ozgartirish");
        row_one.add(add);


        KeyboardButton show = new KeyboardButton("Familiya ozgartirish");
        row_one.add(show);

        KeyboardButton delete = new KeyboardButton("Parol ozgartirish");
        row_one.add(delete);

        buttonList.add(row_one);

        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(buttonList);
        replyMarkup.setIsPersistent(true);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);
        replyMarkup.setInputFieldPlaceholder("Knopkalardan birini tanlang..");

        sendMessage.setReplyMarkup(replyMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
}
