package com.example.telegrambot.response;

import com.example.telegrambot.entity.BookEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BookListResponse extends BaseResponse {

    public BookListResponse(Iterable<BookEntity> data) {
        super(true, "Список книг");
        this.data = data;
    }

    private Iterable<BookEntity> data;
    @Override
    public String toString(){
        return "Книги из библиотеки\n"+data;
    }
}
