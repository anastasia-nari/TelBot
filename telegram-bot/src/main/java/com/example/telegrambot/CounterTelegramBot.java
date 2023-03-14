package com.example.telegrambot;

import com.example.telegrambot.components.BotCommands;
import com.example.telegrambot.components.Buttons;
import com.example.telegrambot.config.BotConfig;
import com.example.telegrambot.response.BookListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
public class CounterTelegramBot extends TelegramLongPollingBot implements BotCommands {
    final BotConfig config;


    public CounterTelegramBot(BotConfig config) {
        this.config = config;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }


    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String member = update.getMessage().getFrom().getFirstName();
            String[] mas = messageText.split(" ");

            switch (mas[0]){
                case "/start":
                    startBot(chatId, member);
                    break;
                case "/help":
                    sendHelpText(chatId, HELP_TEXT);
                    break;
                case "/all": getAllBook(chatId);
                break;
                case "/search":
                    searchBook(chatId, mas[1]);
                    break;
                default: log.info("Unexpected message");
            }
        }
    }

    private void getAllBook(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        ResponseEntity<BookListResponse> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:9095/api/v1/books/all",BookListResponse.class);
        System.out.println(responseEntity.getBody().getData());
        message.setText(responseEntity.getBody().getData().toString());
        try {
            execute(message);
            log.info("Reply sent");
        }
        catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    private void searchBook(long chatId,String title){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        ResponseEntity<BookListResponse>responseEntity= new RestTemplate().getForEntity(
                "http://localhost:9095/api/v1/books/search?title="+ title,BookListResponse.class);
        System.out.println(responseEntity.getBody().getData());
        message.setText(responseEntity.getBody().getData().toString());
        try {
            execute(message);
            log.info("Reply sent");
        }
        catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hi, " + userName + "! I'm a Telegram bot.'");
        message.setReplyMarkup(Buttons.inlineMarkup());


        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


    private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);


        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
}