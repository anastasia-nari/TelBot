package com.example.telegrambot.response;

import com.example.telegrambot.entity.BookEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BookResponse extends BaseResponse {
    public BookResponse(boolean success, String message, BookEntity publisher) {
        super(success,message);
        this.publisher=publisher;
    }
    public BookResponse (BookEntity publishing){super(true, "Publishing data");}
    private BookEntity publisher;
}
